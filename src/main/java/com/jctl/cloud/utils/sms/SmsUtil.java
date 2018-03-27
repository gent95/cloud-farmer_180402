package com.jctl.cloud.utils.sms;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.smshistory.entity.SmsHistory;
import com.jctl.cloud.manager.smshistory.service.SmsHistoryService;
import com.jctl.cloud.utils.IpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by gent on 2017/3/13.
 */
public class SmsUtil {

    private static HttpsRequest httpRequest = new HttpsRequest();

    private static Long TIME = 1000 * 60 * 2L;


    private static SmsHistoryService smsHistoryService = SpringContextHolder.getBean(SmsHistoryService.class);


    /**
     * 短信发送方法
     *
     * @param request
     * @param phone
     * @return
     */
    public static String getVerification(HttpServletRequest request, String phone) {
        SmsHistory history = smsHistoryService.getLastSms(phone);
        if (history == null) {
            return sendCode(request, phone);
        }
        if ((new Date().getTime() - history.getCreateDate().getTime()) > TIME) {
            return sendCode(request, phone);
        }
        return "0";
    }

    /**
     * 短信发送方法
     * @return
     */
    public static void sendSMS(String mobile,String messsage) {



    }


    /**
     * 发送短信
     *
     * @param request
     * @param phone
     * @return
     */
    private static String sendCode(HttpServletRequest request, String phone) {
        HttpSession session = request.getSession();
        httpRequest.getBalance("POST", request);
        httpRequest.sendSms("POST", session, request, phone);
        String mesCheckCode = session.getAttribute(phone).toString();
        smsHistoryService.save(new SmsHistory(phone, IpUtil.getIpAddress(request)));
        return mesCheckCode;
    }
}
