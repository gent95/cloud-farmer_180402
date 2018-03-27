package com.jctl.cloud.manager.console;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import com.jctl.cloud.utils.NodeControlUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Created by gent on 2017/3/2.
 */
public class NodeConsole implements Job {


    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);

    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private NodeControlUtil nodeControlUtil = SpringContextHolder.getBean(NodeControlUtil.class);

    public void execute(JobExecutionContext context) {
        String nodeNumAndIndex = context.getTrigger().getName();
        String index = nodeNumAndIndex.substring(nodeNumAndIndex.length() - 1);
        String nodeId = nodeNumAndIndex.substring(0, nodeNumAndIndex.length() - 1);

        Node node = nodeService.get(nodeId);

        Relay relay = relayService.get(node.getRelayId().toString());
        if (relay == null) {
            return;
        }
        IoSessionEntity session = MinaLongConnServer.sessionMap.get(relay.getRelayNum());
        if (session != null) {
            try {
                if (index.equals("0")) {
                    nodeControlUtil.refreshNode(session, node.getNodeNum());
                } else if (index.equals("1")) {
                    nodeControlUtil.openNode(session, node.getNodeNum());
                } else if (index.equals("2")) {
                    nodeControlUtil.closeNode(session, node.getNodeNum());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
