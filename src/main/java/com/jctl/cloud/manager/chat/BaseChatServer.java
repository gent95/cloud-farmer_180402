package com.jctl.cloud.manager.chat;

import javax.websocket.*;

/**
 * Created by gent on 2017/5/12.
 */
public class BaseChatServer {
    /**
     * 连接打开时触发
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.print("连接成功");
    }

    /**
     * 接收客户端信息并反馈消息到客户端
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){

        session.getAsyncRemote().sendText("服务器发送消息");

    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session){

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
