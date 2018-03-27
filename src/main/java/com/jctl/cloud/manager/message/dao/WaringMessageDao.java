package com.jctl.cloud.manager.message.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.modules.sys.entity.User;

import java.util.List;

/**
 * 报警信息DAO接口
 *
 * @author kay
 * @version 2017-03-07
 */
@MyBatisDao
public interface WaringMessageDao extends CrudDao<WaringMessage> {


    List<WaringMessage> findAllMessage(WaringMessage waringMessage);


    WaringMessage getByNodeAndProperty(WaringMessage search);


    List<WaringMessage> findListByUser(User user);

}