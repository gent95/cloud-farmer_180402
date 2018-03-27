/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodehistory.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 历史命令Entity
 * @author kay
 * @version 2017-05-25
 */
public class NodeHistory extends DataEntity<NodeHistory> {
	
	private static final long serialVersionUID = 1L;
	private Long relayId;		// 中继
	private Long nodeId;		// 节点
	private String orders;		// 命令详情
	
	public NodeHistory() {
		super();
	}

	public NodeHistory(String id){
		super(id);
	}

	public Long getRelayId() {
		return relayId;
	}

	public void setRelayId(Long relayId) {
		this.relayId = relayId;
	}
	
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	@Length(min=0, max=255, message="命令详情长度必须介于 0 和 255 之间")
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}
	
}