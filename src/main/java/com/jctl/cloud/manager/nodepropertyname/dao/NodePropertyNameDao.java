/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodepropertyname.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.nodepropertyname.entity.NodePropertyName;

/**
 * 别名DAO接口
 * @author kay
 * @version 2017-05-05
 */
@MyBatisDao
public interface NodePropertyNameDao extends CrudDao<NodePropertyName> {

    NodePropertyName getByUserAndType(NodePropertyName search);
}