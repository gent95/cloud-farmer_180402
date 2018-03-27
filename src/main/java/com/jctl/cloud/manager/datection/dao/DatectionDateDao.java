/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.datection.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.datection.entity.DatectionDate;

import java.util.List;

/**
 * 设备数据DAO接口
 * @author mjt
 * @version 2017-05-04
 */
@MyBatisDao
public interface DatectionDateDao extends CrudDao<DatectionDate> {
    List<DatectionDate> findByCreateTime();
    List<DatectionDate> findTopFirstLine();
	
}