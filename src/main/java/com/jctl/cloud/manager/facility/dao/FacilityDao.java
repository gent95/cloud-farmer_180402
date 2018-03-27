/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.facility.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.facility.entity.Facility;

/**
 * 气象站设备DAO接口
 * @author gent
 * @version 2017-05-04
 */
@MyBatisDao
public interface FacilityDao extends CrudDao<Facility> {
	
}