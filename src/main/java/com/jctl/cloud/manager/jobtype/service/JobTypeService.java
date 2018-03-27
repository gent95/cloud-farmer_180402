/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.jobtype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.jobtype.dao.JobTypeDao;
import com.jctl.cloud.manager.jobtype.entity.JobType;

/**
 * 作业类型Service
 * @author tulanlan
 * @version 2017-04-17
 */
@Service
@Transactional(readOnly = true)
public class JobTypeService extends CrudService<JobTypeDao, JobType> {

	public JobType get(String id) {
		return super.get(id);
	}
	
	public List<JobType> findList(JobType jobType) {
		return super.findList(jobType);
	}
	
	public Page<JobType> findPage(Page<JobType> page, JobType jobType) {
		return super.findPage(page, jobType);
	}
	
	@Transactional(readOnly = false)
	public void save(JobType jobType) {
		super.save(jobType);
    }
	
	@Transactional(readOnly = false)
	public void delete(JobType jobType) {
		super.delete(jobType);
	}
	
}