/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.version.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 安卓版本Entity
 * @author kay
 * @version 2017-06-09
 */
public class AppVersion extends DataEntity<AppVersion> {
	
	private static final long serialVersionUID = 1L;
	private String inVersion;		// 内部版本
	private String outVersion;		// 外部版本
	private String remark;		// 备注
	private String status;		// 状态
	private String url;		// 下载地址
	private String autoUpdate;		// 自动更新
	
	public AppVersion() {
		super();
	}

	public AppVersion(String id){
		super(id);
	}

	@Length(min=0, max=126, message="内部版本长度必须介于 0 和 126 之间")
	public String getInVersion() {
		return inVersion;
	}

	public void setInVersion(String inVersion) {
		this.inVersion = inVersion;
	}
	
	@Length(min=0, max=255, message="外部版本长度必须介于 0 和 255 之间")
	public String getOutVersion() {
		return outVersion;
	}

	public void setOutVersion(String outVersion) {
		this.outVersion = outVersion;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="下载地址长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=1, message="自动更新长度必须介于 0 和 1 之间")
	public String getAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(String autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	
}