/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.facility.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 气象站设备Entity
 * @author gent
 * @version 2017-05-04
 */
public class Facility extends DataEntity<Facility> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String farmerId;		// farmer_id
	
	public Facility() {
		super();
	}

	public Facility(String id){
		super(id);
	}

	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="farmer_id长度必须介于 0 和 64 之间")
	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	
}