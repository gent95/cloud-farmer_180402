package com.jctl.cloud.manager.chat;

import com.jctl.cloud.common.utils.DateUtils;
import net.sf.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Vector;

/**
 * Created by gent on 2017/4/19.
 */
@ServerEndpoint("/chatServer")
public class ChatServer{
    private static Vector<Session> room = new Vector<Session>();


    /**
     * 连接打开时触发
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.print("连接成功");
        room.addElement(session);
    }

    /**
     * 接收客户端信息并反馈消息到客户端
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){

        System.out.println("接收消息,SessionId 是:" + session.getId());
        JSONObject jsonObject = JSONObject.fromObject(message);
        System.out.println("用户名是:" + jsonObject.get("nickname") + "信息是:" + jsonObject.get("content"));
        jsonObject.put("date", DateUtils.formatDateTime(new Date()));
        for (Session sec : room) {
            System.out.println("判断是:"+sec.equals(session));
            jsonObject.put("isSelf",sec.equals(session));
            sec.getAsyncRemote().sendText(jsonObject.toString());
        }
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session){
        room.remove(session);
    }

    /**
     * 连接异常
     */
    @OnError
    public void onError(Throwable t){
        System.out.println("连接异常....");
        t.printStackTrace();
    }
}
