/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodepropertyname.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 别名Entity
 * @author kay
 * @version 2017-05-05
 */
public class NodePropertyName extends DataEntity<NodePropertyName> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 命名
	private String type;		// 类型
	private String nodeId;		// 类型

    public NodePropertyName(String nodeId, Object o) {
    	this.nodeId=nodeId;
    }

    public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public NodePropertyName() {
		super();
	}

	public NodePropertyName(String id){
		super(id);
	}

	@Length(min=0, max=255, message="命名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}