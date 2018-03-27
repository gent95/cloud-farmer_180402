/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodepropertyname.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.nodepropertyname.entity.NodePropertyName;
import com.jctl.cloud.manager.nodepropertyname.dao.NodePropertyNameDao;

/**
 * 别名Service
 * @author kay
 * @version 2017-05-05
 */
@Service
@Transactional(readOnly = true)
public class NodePropertyNameService extends CrudService<NodePropertyNameDao, NodePropertyName> {

	@Autowired
	private NodePropertyNameDao nodePropertyNameDao;

	public NodePropertyName get(String id) {
		return super.get(id);
	}
	
	public List<NodePropertyName> findList(NodePropertyName nodePropertyName) {
		return super.findList(nodePropertyName);
	}
	
	public Page<NodePropertyName> findPage(Page<NodePropertyName> page, NodePropertyName nodePropertyName) {
		return super.findPage(page, nodePropertyName);
	}
	
	@Transactional(readOnly = false)
	public void save(NodePropertyName nodePropertyName) {
		super.save(nodePropertyName);
	}
	
	@Transactional(readOnly = false)
	public void delete(NodePropertyName nodePropertyName) {
		super.delete(nodePropertyName);
	}


	public NodePropertyName getByUserAndType(NodePropertyName search) {
		return nodePropertyNameDao.getByUserAndType(search);
	}
}