/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmerland.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.farmerland.entity.Farmland;

import java.util.List;

/**
 * 农田(大棚)DAO接口
 *
 * @author kay
 * @version 2017-02-25
 */
@MyBatisDao
public interface FarmlandDao extends CrudDao<Farmland> {
    Integer findFarmerlandNumByFarmerId(String farmerId);

    void updateById(Farmland farmland);

    void updateFarmland(Farmland farmland);

    Integer findPengCount(Farmland farmland);

    Integer findTianCount(Farmland farmland);

    List<Farmland> findPlantTypeCountByFarmerId(Farmland farmland);

    List<Farmland> findListAll(Farmland farmland);

    void updateByApp(Farmland farmland);
    List<Farmland> findPlant(Farmland farmland);

    Integer findFarmlandNum(Farmland farmland);
}