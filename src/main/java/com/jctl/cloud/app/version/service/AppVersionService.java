/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.version.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.app.version.entity.AppVersion;
import com.jctl.cloud.app.version.dao.AppVersionDao;

/**
 * 安卓版本Service
 *
 * @author kay
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class AppVersionService extends CrudService<AppVersionDao, AppVersion> {

    @Autowired
    private AppVersionDao appVersionDao;

    public AppVersion get(String id) {
        return super.get(id);
    }

    public List<AppVersion> findList(AppVersion appVersion) {
        return super.findList(appVersion);
    }

    public Page<AppVersion> findPage(Page<AppVersion> page, AppVersion appVersion) {
        return super.findPage(page, appVersion);
    }

    @Transactional(readOnly = false)
    public void save(AppVersion appVersion) {
        super.save(appVersion);
    }

    @Transactional(readOnly = false)
    public void delete(AppVersion appVersion) {
        super.delete(appVersion);
    }

    public AppVersion findNewVersion() {
        return appVersionDao.findNewVersion();
    }
}