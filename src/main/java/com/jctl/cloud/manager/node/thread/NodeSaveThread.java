package com.jctl.cloud.manager.node.thread;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.mina.entity.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public class NodeSaveThread extends Thread {

    private ResultSet resultSet;
    private String relayMac;

    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);


    public NodeSaveThread() {

    }

    public NodeSaveThread(ResultSet resultSet, String relayMac) {
        this.resultSet = resultSet;
        this.relayMac = relayMac;
    }

    public void run() {

        try {
            Thread.sleep(3000L);
            //得到中继对象
            Relay relay = relayService.getByMac(relayMac);
            //得到中继下面的所有节点
            List<String> clintMacs = resultSet.getGatewayResultSet().getClientMacList();
           //如果中继下面的节点为空
            if(clintMacs!=null) {
                //循环遍历中继下的节点
                for (String cli : clintMacs) {
                    //查询该中继下的节点是否已经存在数据库中
                    Node tem = nodeService.findByNodeNum(cli);
                    //如果节点对象不等于并且节点的中继id不等该中继对象的id
                    if (tem != null && !tem.getRelayId().toString().equals(relay.getId())) {
                        //删除数据库中该节点信息
                        nodeService.deleteByNodeNum(cli);
                    }
                }
            }
            Node node = new Node();
            //将中继信息添加到节点中
            node.setRelayId(relay.getId());
            //查询数据库中该中继下的节点
            List<Node> nodes = nodeService.findList(node);
            List<String> save = new ArrayList<>();
                //遍历数据库中该中继下的节点
            for (Node old : nodes) {
                //将节点编号保存到list集合中
                save.add(old.getNodeNum());
            }

            for (String cli : save) {
                if(clintMacs==null){
                    nodeService.deleteByNodeNum(cli);
                }else {
                    if (!clintMacs.contains(cli)) {
                        nodeService.deleteByNodeNum(cli);
                    }
                }
            }


            nodeService.saveOrUpdate(resultSet, relay.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
