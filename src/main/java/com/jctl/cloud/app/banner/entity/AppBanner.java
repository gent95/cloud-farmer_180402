/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.banner.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * APP轮播Entity
 * @author 刘凯
 * @version 2017-04-21
 */
public class AppBanner extends DataEntity<AppBanner> {
	
	private static final long serialVersionUID = 1L;
	private String url;		// url
	private String path;		// 路径
	
	public AppBanner() {
		super();
	}

	public AppBanner(String id){
		super(id);
	}

	@Length(min=0, max=255, message="url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=255, message="路径长度必须介于 0 和 255 之间")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}