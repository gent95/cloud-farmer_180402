package com.jctl.cloud.mina.timingtask;

import com.jctl.cloud.mina.thread.RefreshNodePoolExecutorThread;
import com.jctl.cloud.mina.thread.RefreshRelayAndNodeInfoThread;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时从刷新节点信息
 * Created by lewKay on 2017/2/28.
 */

@Service
@Lazy(false)
public class TimingCollectionRelayNodeInfoTask {

    public TimingCollectionRelayNodeInfoTask() {
    }
    /**
     * 定时任务，更新节点信息
         */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void collectionRelayAndNodeInfoTask() {
    //处理并发可能因相互竞争造成阻塞
//        new Thread(new RefreshRelayAndNodeInfoThread()).start();
//        RefreshNodePoolExecutorThread.getNodePoolExecutor().execute(new RefreshRelayAndNodeInfoThread());
    }

}
