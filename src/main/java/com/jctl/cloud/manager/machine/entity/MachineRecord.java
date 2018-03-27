/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.machine.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jctl.cloud.modules.sys.entity.User;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 加工记录Entity
 * @author ll
 * @version 2017-04-17
 */
public class MachineRecord extends DataEntity<MachineRecord> {
	
	private static final long serialVersionUID = 1L;
	private String farmerId;		// 农场编号
	private String farmlandId;		// 农田编号
	private String plantName;		// 物种名称
	private String machineMode;		// 加工方式
	private String totalWeight;		// 加工总重量
	private String singleWeight;		// 单件重量
	private String admin;		// 负责人
	private Date machineTime;		// 加工时间
	private String remark;		// 备注
	private User user;		// 所属农场主
	private String usedId;//所属农户

	public String getUsedId() {
		return usedId;
	}

	public void setUsedId(String usedId) {
		this.usedId = usedId;
	}

	public MachineRecord() {
		super();
	}

	public MachineRecord(String id){
		super(id);
	}

	@Length(min=0, max=64, message="农场编号长度必须介于 0 和 64 之间")
	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	
	@Length(min=0, max=64, message="农田编号长度必须介于 0 和 64 之间")
	public String getFarmlandId() {
		return farmlandId;
	}

	public void setFarmlandId(String farmlandId) {
		this.farmlandId = farmlandId;
	}
	
	@Length(min=0, max=64, message="物种名称长度必须介于 0 和 64 之间")
	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
	@Length(min=0, max=64, message="加工方式长度必须介于 0 和 64 之间")
	public String getMachineMode() {
		return machineMode;
	}

	public void setMachineMode(String machineMode) {
		this.machineMode = machineMode;
	}
	
	@Length(min=0, max=64, message="加工总重量长度必须介于 0 和 64 之间")
	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	@Length(min=0, max=64, message="单件重量长度必须介于 0 和 64 之间")
	public String getSingleWeight() {
		return singleWeight;
	}

	public void setSingleWeight(String singleWeight) {
		this.singleWeight = singleWeight;
	}
	
	@Length(min=0, max=64, message="负责人长度必须介于 0 和 64 之间")
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMachineTime() {
		return machineTime;
	}

	public void setMachineTime(Date machineTime) {
		this.machineTime = machineTime;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}