package com.jctl.cloud.mina.handler;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**
 * Created by  on 2017/5/15.
 */
public class MinaUdpLongConnServerHandler implements IoHandler {

    private final String GATEWAY = "Gateway:\n";

    //建立连接触发的事件
    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {

        System.out.println("sessionCreated");

    }

    //打开连接触发的事件
    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {

        System.out.println("sessionOpened");
    }

    //连接关闭触发的事件
    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        sendGateway(ioSession);
        System.out.println("sessionClosed");
    }

    private void sendGateway(IoSession ioSession) {
        IoBuffer buffer1 = IoBuffer.wrap(GATEWAY.getBytes());
        ioSession.write(buffer1);
    }

    //会话空闲
    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        System.out.println("sessionIdle");
    }

    //抛出异常触发的事件
    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        System.out.println("exceptionCaught");
    }

    //Server接收到UDP请求触发的事件
    @Override
    public void messageReceived(IoSession ioSession, Object message) throws Exception {
        System.out.println(getMessage( message));

        sendMessage(ioSession, GATEWAY);
    }

    //messageSent是Server响应给Clinet成功后触发的事件
    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("messageSent");
    }


    public void sendMessage(IoSession ioSession, Object message) {
        IoBuffer buffer1 = IoBuffer.wrap(message.toString().getBytes());
        ioSession.write(buffer1);
    }


    public String getMessage(Object message) {
        IoBuffer buf =(IoBuffer)message;
        byte[] b = new byte [buf.limit()];
        buf.get(b);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buffer.append((char) b [i]);
        }
        return buffer.toString();
    }
}
