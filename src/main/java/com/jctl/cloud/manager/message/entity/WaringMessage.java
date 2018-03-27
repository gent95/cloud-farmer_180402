/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.message.entity;

import com.jctl.cloud.manager.node.entity.Node;
import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 报警信息Entity
 * @author kay
 * @version 2017-03-07
 */
public class WaringMessage extends DataEntity<WaringMessage> {
	
	private static final long serialVersionUID = 1L;
	private String nodeNum;		// 节点编号
	private String nodeName;//节点名称
	private String message;		// 报警信息
	private List<String> nodes;		// 报警信息
	private String status;		// 报警信息
	private String property;		//预警项
	private String nodeId;		//预警项

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public WaringMessage() {
		super();
	}

	public WaringMessage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="节点编号长度必须介于 0 和 64 之间")
	public String getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}

	@Length(min=0, max=255, message="报警信息长度必须介于 0 和 255 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}