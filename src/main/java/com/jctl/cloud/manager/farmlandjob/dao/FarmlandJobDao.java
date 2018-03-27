/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmlandjob.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.farmlandjob.entity.FarmlandJob;

/**
 * 作业管理DAO接口
 * @author ll
 * @version 2017-04-17
 */
@MyBatisDao
public interface FarmlandJobDao extends CrudDao<FarmlandJob> {
	
}