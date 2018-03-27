/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodehistory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.nodehistory.entity.NodeHistory;
import com.jctl.cloud.manager.nodehistory.dao.NodeHistoryDao;

/**
 * 历史命令Service
 * @author kay
 * @version 2017-05-25
 */
@Service
@Transactional(readOnly = true)
public class NodeHistoryService extends CrudService<NodeHistoryDao, NodeHistory> {

	public NodeHistory get(String id) {
		return super.get(id);
	}
	
	public List<NodeHistory> findList(NodeHistory nodeHistory) {
		return super.findList(nodeHistory);
	}
	
	public Page<NodeHistory> findPage(Page<NodeHistory> page, NodeHistory nodeHistory) {
		return super.findPage(page, nodeHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(NodeHistory nodeHistory) {
		super.save(nodeHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(NodeHistory nodeHistory) {
		super.delete(nodeHistory);
	}
	
}