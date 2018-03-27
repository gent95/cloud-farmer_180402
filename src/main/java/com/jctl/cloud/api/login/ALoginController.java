package com.jctl.cloud.api.login;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.entity.LoginResult;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Lew on 2017/3/23.
 */

@Controller
@RequestMapping("login")
public class ALoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "doLogin")
    @ResponseBody
    public Map doLogin(User user, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            LoginResult flag = FrontUserUtils.doLogin(user, request);
            if (flag == LoginResult.登录成功) {

                Map<String, Object> map = new HashMap<>();
                String[] propertys = new String[]{"name", "photo", "singleId"};
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(FrontUserUtils.getUserBySession(), property));
                }
                result.put("flag", 1);
                result.put("user", map);
                return result;
            } else if (flag == LoginResult.用户被锁定) {
                result.put("flag", 0);
                result.put("msg", "用户被锁定，请联系管理员！");
                return result;
            } else if (flag == LoginResult.登录失败) {
                result.put("flag", 0);
                result.put("msg", "账号或密码错误！");
                return result;
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 登陆信息会话校验
     *
     * @return
     */
    @RequestMapping(value = "doLoginSingleId")
    @ResponseBody
    public Map doLoginSingleId() {
        Map result = Maps.newHashMap();
        try {
            User user = FrontUserUtils.getUser();
            if (user.getSingleId() == null ||user.getSingleId().equals("")) {
                result.put("flag", 0);
                result.put("msg", "会话已过期，请重新登陆！");
                return result;
            }
            user.setLoginDate(new Date());
            userService.update(user);
            String[] propertys = new String[]{"name", "photo"};
            Map<String, Object> map = new HashMap<>();
            for (String property : propertys) {
                map.put(property, Reflections.invokeGetter(user, property));
            }
            result.put("flag", 1);
            result.put("user", map);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "loginOut")
    @ResponseBody
    public Map loginOut() {
        Map result = Maps.newHashMap();
        try {
            FrontUserUtils.cleanCache();
            result.put("flag", 1);
            result.put("msg", "退出成功！");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }


}
