/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.machine.service;

import java.util.List;

import com.jctl.cloud.manager.relay.entity.Relay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.machine.entity.MachineRecord;
import com.jctl.cloud.manager.machine.dao.MachineRecordDao;

/**
 * 加工记录Service
 * @author ll
 * @version 2017-04-17
 */
@Service
@Transactional(readOnly = true)
public class MachineRecordService extends CrudService<MachineRecordDao, MachineRecord> {

	public MachineRecord get(String id) {
		return super.get(id);
	}
	
	public List<MachineRecord> findList(MachineRecord machineRecord) {
		return super.findList(machineRecord);
	}
	
	public Page<MachineRecord> findPage(Page<MachineRecord> page, MachineRecord machineRecord) {
		return super.findPage(page, machineRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(MachineRecord machineRecord) {
		super.save(machineRecord);
    }
	
	@Transactional(readOnly = false)
	public void delete(MachineRecord machineRecord) {
		super.delete(machineRecord);
	}
	
}