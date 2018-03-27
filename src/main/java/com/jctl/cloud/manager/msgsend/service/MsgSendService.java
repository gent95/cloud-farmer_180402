/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.msgsend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.msgsend.entity.MsgSend;
import com.jctl.cloud.manager.msgsend.dao.MsgSendDao;

/**
 * 短信发送记录Service
 *
 * @author ll
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class MsgSendService extends CrudService<MsgSendDao, MsgSend> {
    @Autowired
    private MsgSendDao msgSendDao;

    public List<MsgSend> findListNumber(MsgSend send) {
        return msgSendDao.findListNumber(send);
    }

    public MsgSend get(String id) {
        return super.get(id);
    }

    public List<MsgSend> findList(MsgSend msgSend) {
        return super.findList(msgSend);
    }

    public Page<MsgSend> findPage(Page<MsgSend> page, MsgSend msgSend) {
        return super.findPage(page, msgSend);
    }

    @Transactional(readOnly = false)
    public void save(MsgSend msgSend) {
        super.save(msgSend);
    }

    @Transactional(readOnly = false)
    public void delete(MsgSend msgSend) {
        super.delete(msgSend);
    }

}