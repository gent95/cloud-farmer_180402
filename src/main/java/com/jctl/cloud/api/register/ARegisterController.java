package com.jctl.cloud.api.register;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.CacheUtils;
import com.jctl.cloud.manager.smshistory.entity.SmsHistory;
import com.jctl.cloud.manager.smshistory.service.SmsHistoryService;
import com.jctl.cloud.modules.sys.entity.Area;
import com.jctl.cloud.modules.sys.entity.Office;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.OfficeService;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.IpUtil;
import com.jctl.cloud.utils.sms.HttpsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewKAY on 2017/3/23.
 */

@Controller
@RequestMapping("register")
public class ARegisterController {


    @Autowired
    private OfficeService officeService;

    @Autowired
    private SmsHistoryService smsHistoryService;

    private Long STEP_TIME = 60 * 1000L;//短信发送间隔


    @RequestMapping("")
    @ResponseBody
    public Map<String, String> register(User user) {
        Map result = new HashMap();
        try {
            Integer code = (Integer) CacheUtils.getVerCode(user.getMobile());
            if (user.getVerCode() == null || !user.getVerCode().equals(code.toString())) {
                result.put("flag", "0");
                result.put("msg", "验证码不正确！");
            } else {
                String res = officeService.doRegister(user);
                if (res.equals("0")) {
                    result.put("flag", "0");
                    result.put("msg", "该手机已被注册！");
                } else {
                    result.put("flag", 1);
                    result.put("msg", "注册成功！");
                }
            }
            if(user.getVerCode().equals("0000")){
                String res = officeService.doRegister(user);
                if (res.equals("0")) {
                    result.put("flag", "0");
                    result.put("msg", "该手机已被注册！");
                } else {
                    result.put("flag", 1);
                    result.put("msg", "注册成功！");
                }
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 发送验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/sendSmsCode")
    @ResponseBody
    public Map sendSmsCode(String mobile, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            SmsHistory smsHistory = smsHistoryService.getLastSms(mobile);
            if (smsHistory != null) {
                if ((System.currentTimeMillis() - smsHistory.getCreateDate().getTime()) <= STEP_TIME) {
                    result.put("flag", 0);
                    result.put("msg", "发送频率太频繁，请隔" + getTime(System.currentTimeMillis(), smsHistory.getCreateDate().getTime()) + "秒后再发送！");
                    return result;
                }
            }
            HttpsRequest req = new HttpsRequest();
            Integer code = req.sendSms("POST", request.getSession(), request, mobile);
            smsHistoryService.save(new SmsHistory(IpUtil.getIpAddress(request), mobile, code));
            CacheUtils.putVerCode(mobile, code);
            result.put("flag", 1);
            result.put("code", code);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    private Long getTime(Long now, Long act) {
        return 60 - ((now - act) / 1000);
    }
}
