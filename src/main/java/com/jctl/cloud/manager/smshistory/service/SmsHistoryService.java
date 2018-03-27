/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.smshistory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.smshistory.entity.SmsHistory;
import com.jctl.cloud.manager.smshistory.dao.SmsHistoryDao;

/**
 * 短信记录Service
 * @author kay
 * @version 2017-05-04
 */
@Service
@Transactional(readOnly = true)
public class SmsHistoryService extends CrudService<SmsHistoryDao, SmsHistory> {
	@Autowired
	private SmsHistoryDao smsHistoryDao;

	public SmsHistory get(String id) {
		return super.get(id);
	}
	
	public List<SmsHistory> findList(SmsHistory smsHistory) {
		return super.findList(smsHistory);
	}
	
	public Page<SmsHistory> findPage(Page<SmsHistory> page, SmsHistory smsHistory) {
		return super.findPage(page, smsHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsHistory smsHistory) {
		super.save(smsHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsHistory smsHistory) {
		super.delete(smsHistory);
	}

    public SmsHistory getLastSms(String mobile) {
		return smsHistoryDao.getLastSms(mobile);
    }
}