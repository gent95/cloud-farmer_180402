/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.facility.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.facility.dao.FacilityDao;
import com.jctl.cloud.manager.facility.entity.Facility;

/**
 * 气象站设备Service
 * @author gent
 * @version 2017-05-04
 */
@Service
@Transactional(readOnly = true)
public class FacilityService extends CrudService<FacilityDao, Facility> {

	public Facility get(String id) {
		return super.get(id);
	}
	
	public List<Facility> findList(Facility facility) {
		return super.findList(facility);
	}
	
	public Page<Facility> findPage(Page<Facility> page, Facility facility) {
		return super.findPage(page, facility);
	}
	
	@Transactional(readOnly = false)
	public void save(Facility facility) {
		super.save(facility);
	}
	
	@Transactional(readOnly = false)
	public void delete(Facility facility) {
		super.delete(facility);
	}
	
}