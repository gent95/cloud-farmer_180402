/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmlandjob.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.farmlandjob.dao.FarmlandJobDao;
import com.jctl.cloud.manager.farmlandjob.entity.FarmlandJob;

/**
 * 作业管理Service
 * @author ll
 * @version 2017-04-17
 */
@Service
@Transactional(readOnly = true)
public class FarmlandJobService extends CrudService<FarmlandJobDao, FarmlandJob> {

	public FarmlandJob get(String id) {
		return super.get(id);
	}
	
	public List<FarmlandJob> findList(FarmlandJob farmlandJob) {
		return super.findList(farmlandJob);
	}
	
	public Page<FarmlandJob> findPage(Page<FarmlandJob> page, FarmlandJob farmlandJob) {
		return super.findPage(page, farmlandJob);
	}
	
	@Transactional(readOnly = false)
	public void save(FarmlandJob farmlandJob) {
		super.save(farmlandJob);
    }
	
	@Transactional(readOnly = false)
	public void delete(FarmlandJob farmlandJob) {
		super.delete(farmlandJob);
	}
	
}