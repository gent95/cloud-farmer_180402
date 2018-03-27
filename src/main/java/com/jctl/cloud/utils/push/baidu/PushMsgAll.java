package com.jctl.cloud.utils.push.baidu;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.jctl.cloud.common.config.JCTLConfig;

/**
 * Created by gent on 2017/3/29.
 */
public class PushMsgAll implements JCTLConfig {
    public static boolean push(String message) throws PushClientException, PushServerException {
        PushKeyPair pair = new PushKeyPair(APIKEY, SECREKEY);
        BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);
        try {
            PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(3600)).addMessageType(0)
                    .addMessage(message)
                    .addSendTime(System.currentTimeMillis() / 1000 + 70).addDeviceType(3);
            pushClient.pushMsgToAll(request);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
