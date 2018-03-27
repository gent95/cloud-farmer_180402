/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmlandjob.entity;

import com.jctl.cloud.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 作业管理Entity
 * @author ll
 * @version 2017-04-17
 */
public class FarmlandJob extends DataEntity<FarmlandJob> {
	
	private static final long serialVersionUID = 1L;
	private String jobTypeId;		// 作业名称
	private String farmerlandId;		// 作业位置
	private String cropName;		// 作物名称
	private String workerId;		// 作业人员
	private String typeOrName;		// 作业方式或名称
	private String amount;		// 作业量
	private String img;		// 作业图片
	private String flag;		// 审核是否通过 1:通过 0:不通过 2已完成 3待审核
	private String remark;		// 备注
	private User user;//所属农场主
	private String usedId;//所属农户

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsedId() {
		return usedId;
	}

	public void setUsedId(String usedId) {
		this.usedId = usedId;
	}

	public FarmlandJob() {
		super();
	}

	public FarmlandJob(String id){
		super(id);
	}

	@Length(min=0, max=255, message="作业名称长度必须介于 0 和 255 之间")
	public String getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(String jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	
	@Length(min=0, max=64, message="作业位置长度必须介于 0 和 64 之间")
	public String getFarmerlandId() {
		return farmerlandId;
	}

	public void setFarmerlandId(String farmerlandId) {
		this.farmerlandId = farmerlandId;
	}
	
	@Length(min=0, max=255, message="作物名称长度必须介于 0 和 255 之间")
	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
	@Length(min=0, max=64, message="作业人员长度必须介于 0 和 64 之间")
	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	
	@Length(min=0, max=255, message="作业方式或名称长度必须介于 0 和 255 之间")
	public String getTypeOrName() {
		return typeOrName;
	}

	public void setTypeOrName(String typeOrName) {
		this.typeOrName = typeOrName;
	}
	
	@Length(min=0, max=16, message="作业量长度必须介于 0 和 16 之间")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=255, message="作业图片长度必须介于 0 和 255 之间")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=1, message="审核是否通过 1:通过 0:不通过 2已完成长度必须介于 0 和 1 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}