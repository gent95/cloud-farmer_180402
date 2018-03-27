/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.banner.dao;

import com.jctl.cloud.app.banner.entity.AppBanner;
import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;

/**
 * APP轮播DAO接口
 * @author 刘凯
 * @version 2017-04-21
 */
@MyBatisDao
public interface AppBannerDao extends CrudDao<AppBanner> {
	
}