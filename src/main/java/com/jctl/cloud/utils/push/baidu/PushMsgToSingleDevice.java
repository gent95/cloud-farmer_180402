package com.jctl.cloud.utils.push.baidu;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.jctl.cloud.common.config.JCTLConfig;

/**
 * Created by gent on 2017/3/29.
 * 针对某一用户推送
 */
public class PushMsgToSingleDevice implements JCTLConfig {


    /**
     * @param jsonContent 格式{"title":"消息标题(可选)","description":"消息内容"}
     * @param channelId   用户登录时向后台发送,记录在sys_user表mobile
     * @return
     * @throws PushClientException
     * @throws PushServerException
     */
    public static boolean push(String jsonContent, String channelId) throws PushClientException, PushServerException {

        PushKeyPair pair = new PushKeyPair(APIKEY, SECREKEY);
        BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

        try {
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                    addChannelId(channelId).
                    addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                    addMessageType(0).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
                    addMessage(jsonContent).
                    addDeviceType(3);      //设置设备类型，deviceType => 1 for web, 2 for pc,
            pushClient.pushMsgToSingleDevice(request);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
