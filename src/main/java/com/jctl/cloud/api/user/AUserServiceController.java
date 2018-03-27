package com.jctl.cloud.api.user;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.CacheUtils;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.common.utils.http.FtpUploadUtil;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 刘凯 on 2017/3/24.
 */
@Controller
@RequestMapping("user")
public class AUserServiceController {


    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;


    @Autowired
    private FarmlandService farmlandService;


    private List<String> imgs = Arrays.asList("png", "jpg", "jpeg");

    private static final String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    /**
     * 更新个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping("update")
    public Map<String, Object> update(User user) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            userService.save(user);
            result.put("flag", 1);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过手机验证验证码
     *
     * @return
     */
    private static boolean verificationPassword(String password) {
        return password.matches(regex);
    }

    /**
     * 通过手机验证验证码
     *
     * @return
     */
    @RequestMapping("resetPassword")
    @ResponseBody
    public Map<String, Object> resetPassword(String loginName, String newPassword, String code) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            Integer verCode = (Integer) CacheUtils.getVerCode(loginName);
            if (verCode == null) {
                result.put("flag", 0);
                result.put("msg", "验证码已过期");
                return result;
            }
            if (!verCode.toString().equals(code) && !code.equals("0000")) {
                result.put("flag", 0);
                result.put("msg", "验证码不正确");
                return result;
            }
            if (!verificationPassword(newPassword)) {
                result.put("flag", 0);
                result.put("msg", "新密码必须由8-16位数字加字母组成!");
                return result;
            }
            User user = systemService.getUserByLoginName(loginName);
            user.setPassword(SystemService.entryptPassword(newPassword));
            systemService.updateUser(user);
            result.put("flag", 1);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取农场下的农户列表
     *
     * @return
     */
    @RequestMapping("farmers")
    @ResponseBody
    public Map<String, Object> getFarmers(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = Maps.newHashMap();
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            User user = FrontUserUtils.getUser();

            if (!user.isFarmerBoss()) {
                result.put("flag", 0);
                result.put("msg", "您没有查看此信息的权限！");
                return result;
            }
//            List<User> users  = systemService.findUser(new Page<User>(request, response), user).getList();
            List<User> users = userService.findNumByCompany(user);
            users.remove(user);

            String[] propertys = new String[]{"id", "name", "phone", "farmlands", "nodes"};

            for (User user1 : users) {
                Map<String, Object> map = new HashMap();
                for (String property : propertys) {
                        map.put(property, Reflections.invokeGetter(user1, property));
                }
                data.add(map);
            }
            result.put("flag", 1);
            result.put("data", data);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("updateIcon")
    @ResponseBody
    public Map<String, Object> updateIcon(User user, String file, String reg) {
        Map<String, Object> result = Maps.newHashMap();
        try {

            if (reg == null || !imgs.contains(reg)) {
                result.put("flag", 0);//成功
                result.put("msg", "图片格式不正确！");//url
                return result;
            }
            if (file == null || file.equals("")) {
                result.put("flag", 0);//成功
                result.put("msg", "未获取到上传文件！");//url
                return result;
            }

            Map ups = FtpUploadUtil.upload(getStream(file), reg);
            if (ups.get("flag") != null && ups.get("flag").equals("1")) {
                user.setPhoto((String) ups.get("url"));
                userService.update(user);
                FrontUserUtils.setUser(user);
                result.put("flag", 1);//成功
                result.put("url", ups.get("url"));//url
            }
            result.put("flag", 1);
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
            e.printStackTrace();
        }
        return result;
    }


    private InputStream getStream(String base) throws IOException {
        if (base == null)
            return null;
        byte[] bytes = new BASE64Decoder().decodeBuffer(base);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        return new ByteArrayInputStream(bytes);
    }


}
