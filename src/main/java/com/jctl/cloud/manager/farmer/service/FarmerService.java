/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmer.service;

import java.util.List;

import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.farmer.dao.FarmerDao;
import com.jctl.cloud.manager.farmer.entity.Farmer;

/**
 * 农场Service
 *
 * @author lewkay
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class FarmerService extends CrudService<FarmerDao, Farmer> {


    @Autowired
    private FarmerDao farmerDao;

    public Farmer get(String id) {
        return super.get(id);
    }

    public List<Farmer> findList(Farmer farmer) {
        return super.findList(farmer);
    }

    public Page<Farmer> findPage(Page<Farmer> page, Farmer farmer) {
        return super.findPage(page, farmer);
    }

    @Transactional(readOnly = false)
    public void save(Farmer farmer) {
        super.save(farmer);
    }

    @Transactional(readOnly = false)
    public void delete(Farmer farmer) {
        super.delete(farmer);
    }

    public List<Farmer> findListAll(Farmer farmer) {
        return farmerDao.findListAll(farmer);
    }

    public List<Farmer> findListAllFarmers(Farmer farmer) {
        return farmerDao.findListAllFarmers(farmer);
    }

    public List<Farmer> findFarmerByUserId(Farmer farmer){return  farmerDao.findFarmerByUserId(farmer);}
}