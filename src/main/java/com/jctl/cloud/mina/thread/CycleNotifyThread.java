package com.jctl.cloud.mina.thread;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.message.service.WaringMessageService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.utils.NodeControlUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kay on 2017/4/26.
 * 异常采集线程  从项目启动后就开始执行  ，循环密集进行采集
 */
public class CycleNotifyThread extends Thread {


    private static Map map = new HashMap();    //缓存

    private static long CONTINUED_TIME = 20 * 60 * 1000;//异常采集持续时间


    private WaringMessageService waringMessageService = SpringContextHolder.getBean(WaringMessageService.class);

    private WaringCycleService waringCycleService = SpringContextHolder.getBean(WaringCycleService.class);

    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private NodeControlUtil nodeControlUtil = new NodeControlUtil();

    @Override
    public void run() {
        while (true) {
            try {
                List<WaringCycle> list = waringCycleService.findList(new WaringCycle());
                for (WaringCycle cycle : list) {
                    WaringMessage search = new WaringMessage();
                    search.setProperty(cycle.getProperty());
                    search.setNodeNum(cycle.getNodeNum());
                    WaringMessage waringMessage = waringMessageService.getByNodeAndProperty(search);
                    if (waringMessage != null) {
                        if (!isOutOfTime(waringMessage)) {
                            Node node = nodeService.getByNodeNum(waringMessage.getNodeNum());
                            nodeControlUtil.refreshNodeByNodeId(node);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 判断是否超时
     *
     * @param waringMessage
     * @return
     */
    public boolean isOutOfTime(WaringMessage waringMessage) {
        return (new Date().getTime() - waringMessage.getCreateDate().getTime()) > CONTINUED_TIME;
    }
}
