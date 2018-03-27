/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.jobtype.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.jobtype.entity.JobType;

/**
 * 作业类型DAO接口
 * @author tulanlan
 * @version 2017-04-17
 */
@MyBatisDao
public interface JobTypeDao extends CrudDao<JobType> {
	
}