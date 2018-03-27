/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.machine.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.machine.entity.MachineRecord;

/**
 * 加工记录DAO接口
 * @author ll
 * @version 2017-04-17
 */
@MyBatisDao
public interface MachineRecordDao extends CrudDao<MachineRecord> {
	
}