/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.plant.service;

import java.util.Date;
import java.util.List;

import com.jctl.cloud.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.plant.dao.PlantVarietyDao;
import com.jctl.cloud.manager.plant.entity.PlantVariety;

/**
 * 作物管理Service
 *
 * @author ll
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class PlantVarietyService extends CrudService<PlantVarietyDao, PlantVariety> {

    @Autowired
    private PlantVarietyDao plantVarietyDao;

    public PlantVariety get(String id) {
        return super.get(id);
    }

    public List<PlantVariety> findList(PlantVariety plantVariety) {
        return super.findList(plantVariety);
    }

    public Page<PlantVariety> findPage(Page<PlantVariety> page, PlantVariety plantVariety) {
        return super.findPage(page, plantVariety);
    }

    @Transactional(readOnly = false)
    public void save(PlantVariety plantVariety) {
        if (plantVariety.getId() == null || plantVariety.getId().equals("")) {
            plantVariety.setAddTime(new Date());
        }
        super.save(plantVariety);
    }

    @Transactional(readOnly = false)
    public void delete(PlantVariety plantVariety) {
        super.delete(plantVariety);
    }

    public List<PlantVariety> findListByUser(User user) {
        return plantVarietyDao.findListByUser(user);
    }
}