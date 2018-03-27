/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmerland.service;

import java.util.List;

import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.farmerland.dao.FarmlandDao;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;

/**
 * 农田(大棚)Service
 *
 * @author kay
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class FarmlandService extends CrudService<FarmlandDao, Farmland> {
    @Autowired
    private FarmlandDao farmlandDao;

    public Farmland get(String id) {
        return super.get(id);
    }

    public List<Farmland> findList(Farmland farmland) {
        return super.findList(farmland);
    }

    public Page<Farmland> findPage(Page<Farmland> page, Farmland farmland) {
        return super.findPage(page, farmland);
    }

    @Transactional(readOnly = false)
    public void updateFarmland(Farmland farmland) {
        farmlandDao.updateFarmland(farmland);
    }

    @Transactional(readOnly = false)
    public void save(Farmland farmland) {
        if (farmland.getId() == null) {
            User user = UserUtils.getUser();
            if (user.getId() != null && !user.getId().equals("")) {
                farmland.setUser(user);
                farmland.setUsedId(user.getId());
            } else {
                User auser = FrontUserUtils.getUser();
                farmland.setUser(auser);
                farmland.setUsedId(auser.getId());
            }
        }
        super.save(farmland);
    }

    @Transactional(readOnly = false)
    public void updateById(Farmland farmland) {
        farmlandDao.updateById(farmland);
    }

    @Transactional(readOnly = false)
    public void delete(Farmland farmland) {
        super.delete(farmland);
    }

    public Integer findFarmerlandNumByFarmerId(String farmerId) {
        return farmlandDao.findFarmerlandNumByFarmerId(farmerId);
    }

    public Integer findPengCount(Farmland farmland) {
        return farmlandDao.findPengCount(farmland);
    }

    public Integer findTianCount(Farmland farmland) {
        return farmlandDao.findTianCount(farmland);
    }

    public List findPlantTypeCountByFarmerId(Farmland farmland) {
        return farmlandDao.findPlantTypeCountByFarmerId(farmland);
    }

    public List<Farmland> findListAll(Farmland farmland) {
        return farmlandDao.findListAll(farmland);
    }

    @Transactional(readOnly = false)
    public void update(Farmland farmland) {
        farmlandDao.updateByApp(farmland);
    }

    public List<Farmland> findPlant(Farmland farmland) {
        return farmlandDao.findPlant(farmland);
    }

    public Integer findFarmlandNum(Farmland farmland) {
        return farmlandDao.findFarmlandNum(farmland);
    }
}