/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.node.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.node.entity.Node;

import java.util.List;

/**
 * 节点管理DAO接口
 *
 * @author ll
 * @version 2017-02-25
 */
@MyBatisDao
public interface NodeDao extends CrudDao<Node> {
    List<Node> findAllByNum(Node node);
    List<Node> findListAll(Node node);
    Node getByNodeMac(String clintMac);

    Integer getNodeNum(String relayId);

    List<Node> findRefreshList();

    Node getByNodeNum(String nodeNum);

    Node findByNodeNum(String NodeNum);

    Integer getNodeNumberByFarmlandId(String farmlandId);

    List<Node> selectAllNodeByUserId(String id);

    List<Node> getNodeListByFamer(String farmerId);

    void deleteByRelayId(String id);

    void deleteByNodeNum(String nodeNum);
}