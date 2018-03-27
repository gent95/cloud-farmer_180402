/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.version.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.app.version.entity.AppVersion;

/**
 * 安卓版本DAO接口
 * @author kay
 * @version 2017-06-09
 */
@MyBatisDao
public interface AppVersionDao extends CrudDao<AppVersion> {

    AppVersion findNewVersion();
}