/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodehistory.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.nodehistory.entity.NodeHistory;

/**
 * 历史命令DAO接口
 * @author kay
 * @version 2017-05-25
 */
@MyBatisDao
public interface NodeHistoryDao extends CrudDao<NodeHistory> {
	
}