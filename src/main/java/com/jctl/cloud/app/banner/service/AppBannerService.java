/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.banner.service;

import com.jctl.cloud.app.banner.dao.AppBannerDao;
import com.jctl.cloud.app.banner.entity.AppBanner;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * APP轮播Service
 * @author 刘凯
 * @version 2017-04-21
 */
@Service
@Transactional(readOnly = true)
public class AppBannerService extends CrudService<AppBannerDao, AppBanner> {

	public AppBanner get(String id) {
		return super.get(id);
	}
	
	public List<AppBanner> findList(AppBanner appBanner) {
		return super.findList(appBanner);
	}
	
	public Page<AppBanner> findPage(Page<AppBanner> page, AppBanner appBanner) {
		return super.findPage(page, appBanner);
	}
	
	@Transactional(readOnly = false)
	public void save(AppBanner appBanner) {
		super.save(appBanner);
    }
	
	@Transactional(readOnly = false)
	public void delete(AppBanner appBanner) {
		super.delete(appBanner);
	}
	
}