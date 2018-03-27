package com.jctl.cloud.mina.thread.cache;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.thread.RefreshNodePoolExecutorThread;
import com.jctl.cloud.mina.thread.RefreshRelayIfOffThread;
import com.jctl.cloud.mina.utils.cache.IoSessionCacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * 设备通讯Session的哈希吗值维护列表
 * 这些操作都是非线程安全的
 * Created by lewKay on 2017/3/10.
 */
public class RelayConnectionStatusThread extends Thread {


    public RelayConnectionStatusThread() {

    }


    private static Map<String, IoSessionEntity> sessionMap;

    private static Map<String, IoSessionEntity> refresh = new HashMap<>();

    private Log logger = LogFactory.getLog(this.getClass());
    private final Long timeOut = Long.parseLong(Global.getConfig("timeOut").trim());//超时删除长连接

    private final Long minTime = 1000 * 60 * 6L;
    private int back = 0;

    @Override
    public void run() {
        //此处执行SESSION的处理以及过期清除
        while (true) try {
            Thread.sleep(1000);
            sessionMap = IoSessionCacheManager.getSessionMap();
//            sessionMap = MinaLongConnServer.getSessionMap();

            Object[] keys = sessionMap.keySet().toArray();
            Long now = System.currentTimeMillis();

            for (Object obj : keys) {
                IoSessionEntity entity = sessionMap.get(obj);
//                entity.
                if (entity == null) {
                    continue;
                }
//                IoSessionCacheManager.remove(entity);
//                if((now - entity.getAddTime()) > timeOut){
//                    System.out.println("Waring:如果设备一分钟之内未检测到心跳，则视为设备掉线！"+ TimeUtils.toTimeString(now - entity.getAddTime()));
//                }

                if ((now - entity.getAddTime()) > timeOut) {
                    logger.info("clean " + entity.getRelayMac() + "and the cache of this mac.");
//                    sessionMap.remove(entity.getRelayMac());
                    IoSessionCacheManager.remove(entity);
                }
                //刷新获取中继
                if ((now - entity.getAddTime()) < timeOut && (now - entity.getAddTime()) > minTime && back == 0) {
                    if (refresh.get(entity.getRelayMac()) == null) {
//                        new RefreshRelayIfOffThread(entity).start();
                        RefreshNodePoolExecutorThread.getNodePoolExecutor().execute(new RefreshRelayIfOffThread(entity));
                        refresh.put(entity.getRelayMac(), entity);
                    }
                }
            }

            //防止备份长时间占用内存  ，每一个小时也清除一次
            Map backs = IoSessionCacheManager.getSessionMapBackups();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, IoSessionEntity> getRefresh() {
        return refresh;
    }

    public static void setRefresh(Map<String, IoSessionEntity> refresh) {
        RelayConnectionStatusThread.refresh = refresh;
    }
}
