/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.smshistory.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.smshistory.entity.SmsHistory;

/**
 * 短信记录DAO接口
 * @author kay
 * @version 2017-05-04
 */
@MyBatisDao
public interface SmsHistoryDao extends CrudDao<SmsHistory> {

    SmsHistory getLastSms(String mobile);
}