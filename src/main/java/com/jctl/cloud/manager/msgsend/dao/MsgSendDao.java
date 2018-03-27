/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.msgsend.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.msgsend.entity.MsgSend;

import java.util.List;

/**
 * 短信发送记录DAO接口
 * @author ll
 * @version 2017-04-26
 */
@MyBatisDao
public interface MsgSendDao extends CrudDao<MsgSend> {
	List<MsgSend> findListNumber(MsgSend send);
}