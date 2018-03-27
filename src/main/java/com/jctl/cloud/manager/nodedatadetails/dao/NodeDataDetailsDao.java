/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatadetails.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;

import java.util.List;
import java.util.Map;

/**
 * 节点数据详情管理DAO接口
 *
 * @author ll
 * @version 2017-02-27
 */
@MyBatisDao
public interface NodeDataDetailsDao extends CrudDao<NodeDataDetails> {

    List<NodeDataDetails> fetchLastData(NodeDataDetails details);

    NodeDataDetails findDetail(NodeDataDetails search);

    List<NodeDataDetails> findByDay(Map map);

    List<NodeDataDetails> findByMonth(Map map);

    NodeDataDetails getLastByNodeNum(String nodeNum);

    List<NodeDataDetails> findNodeDetailByFarmlandId(String farmlandId);

    List<NodeDataDetails> findAvgData(String farmerId);

    NodeDataDetails lastNodeDetails(Node node);
}