package com.jctl.cloud.mina.thread;

import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import com.jctl.cloud.mina.thread.cache.RelayConnectionStatusThread;

import java.util.Date;
import java.util.Map;

/**
 * 设备掉线发送一次gateway
 * Created by kay on 2017/5/8.
 * 发送3次
 */
public class RefreshRelayIfOffThread extends Thread {

    private IoSessionEntity entity;

    public RefreshRelayIfOffThread(IoSessionEntity entity) {

        this.entity = entity;
    }

    @Override
    public void run() {
//        for (; ; ) {
//            try {
//                IoSessionEntity entity = MinaLongConnServer.sessionMap.get(this.entity.getRelayMac());
//
//                if ((entity.getAddTime() - new Date().getTime()) >= 10 * 60 * 1000L) {
//                    entity.getIoSession().write("Gateway:\n");
//                    break;
//                }
//                Thread.sleep(3 * 60 * 1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    public IoSessionEntity getEntity() {
        return entity;
    }

    public void setEntity(IoSessionEntity entity) {
        this.entity = entity;
    }
}
