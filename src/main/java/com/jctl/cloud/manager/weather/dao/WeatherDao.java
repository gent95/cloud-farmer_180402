/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.weather.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.weather.entity.Weather;

import java.util.List;

/**
 * 气象站数据DAO接口
 * @author ll
 * @version 2017-12-12
 */
@MyBatisDao
public interface WeatherDao extends CrudDao<Weather> {

    List<Weather> findTop8Data();
	
}