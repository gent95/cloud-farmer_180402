/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.message.service;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.common.utils.DateUtils;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.message.dao.WaringMessageDao;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.smshistory.entity.SmsHistory;
import com.jctl.cloud.manager.smshistory.service.SmsHistoryService;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.utils.FrontUserUtils;
import com.jctl.cloud.utils.push.baidu.PushMsgToSingleDevice;
import com.jctl.cloud.utils.sms.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报警信息Service
 *
 * @author kay
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class WaringMessageService extends CrudService<WaringMessageDao, WaringMessage> {

    @Autowired
    private WaringMessageDao waringMessageDao;
    @Autowired
    private SmsHistoryService smsHistoryService;

    @Autowired
    private NodeService nodeService;
    @Autowired
    private SystemService systemService;

    @Autowired
    private FarmlandService farmlandService;

    private long OUT_TIME = 30 * 60 * 1000;

    public List<WaringMessage> findAllMessage(WaringMessage waringMessage) {
        return waringMessageDao.findAllMessage(waringMessage);
    }

    public WaringMessage get(String id) {
        return super.get(id);
    }

    public List<WaringMessage> findList(WaringMessage waringMessage) {
        return super.findList(waringMessage);
    }

    public Page<WaringMessage> findPage(Page<WaringMessage> page, WaringMessage waringMessage) {
        return super.findPage(page, waringMessage);
    }

    @Transactional(readOnly = false)
    public void save(WaringMessage waringMessage) {
        WaringMessage message = getByNodeAndProperty(waringMessage);
        //如果不空   就判断下一个  为空就直接插入
        if (message != null) {
            if ((new Date().getTime() - message.getCreateDate().getTime()) > OUT_TIME) {
                super.save(waringMessage);
            }
        }
        //app的推送
        Node node = nodeService.getByNodeNum(waringMessage.getNodeNum());
        Farmland farmerland = farmlandService.get(node.getFarmlandId());
        if (node.getUsedId() != null && node.getUser()!= null){
           if(node.getUsedId().equals(node.getUser().getId())){
                pushMsg(waringMessage, node.getUser(),farmerland,node);
            }else{
               User used = systemService.get(node.getUsedId());
               pushMsg(waringMessage, used,farmerland,node);
             /*  pushMsg(waringMessage,node.getUser(),farmerland,node);*/
           }
        }
    }

    /**
     * 短信发送
     *
     * @param waringMessage
     */
    public void sendSMS(WaringMessage waringMessage) {

        Node node= nodeService.getByNodeNum(waringMessage.getNodeNum());
        User receive = systemService.getUser(node.getUser().getId());
        if(receive!=null){
            smsHistoryService.getLastSms(receive.getMobile());
            if ((new Date().getTime() - waringMessage.getCreateDate().getTime()) > OUT_TIME) {
                SmsHistory history = new SmsHistory();
                history.setMobile(receive.getMobile());
                smsHistoryService.save(history);
                SmsUtil.getVerification(null,receive.getMobile());
            }
        }
    }

    /**
     * appTUISONG
     *
     * @param msg
     * @param user
     */
    public void pushMsg(WaringMessage msg, User user,Farmland farmland,Node node) {
        try {
            String message = "******";
            if (farmland == null){
                message="{\"msg\":\""+msg.getMessage()+"\",\"node_num\":\""+node.getNodeNum()+"\",\"farmland_num\":\"未绑定\",\"farmland_name\":\"未绑定\",\"date\":\""+ DateUtils.getDateTime()+"\",\"status\":\""+msg.getStatus()+"\",\"openFlag\":\""+node.getOpenFlag()+"\",\"singleId\":\"" + user.getSingleId() + "\"}";
            }else{
               message="{\"msg\":\""+msg.getMessage()+"\",\"node_num\":\""+node.getNodeNum()+"\",\"farmland_num\":\""+farmland.getFarmlandNum()+"\",\"farmland_name\":\""+farmland.getAlias()+"\",\"date\":\""+ DateUtils.getDateTime()+"\",\"status\":\""+msg.getStatus()+"\",\"openFlag\":\""+node.getOpenFlag()+"\",\"singleId\":\"" + user.getSingleId() + "\"}";
            }
            if (user.getChannelId() != null && !user.getChannelId().equals("")&&user.getSingleId()!=null&&!user.getSingleId().equals("")) {
                    PushMsgToSingleDevice.push(message, user.getChannelId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void delete(WaringMessage waringMessage) {
        super.delete(waringMessage);
    }

    public WaringMessage getByNodeAndProperty(WaringMessage search) {
        return waringMessageDao.getByNodeAndProperty(search);
    }

    public static void main(String[] args) {
        WaringMessageService service = new WaringMessageService();
        Map map = new HashMap();
        map.put("msg", "这里有异常信息！");
        try {
            PushMsgToSingleDevice.push(map.toString(), "");
        } catch (PushClientException e) {
            e.printStackTrace();
        } catch (PushServerException e) {
            e.printStackTrace();
        }
    }

    public List<WaringMessage> findListByUser(User user) {
       return waringMessageDao.findListByUser(user);
    }

}