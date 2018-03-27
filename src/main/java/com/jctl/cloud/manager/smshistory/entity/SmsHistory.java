/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.smshistory.entity;

import com.jctl.cloud.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 短信记录Entity
 *
 * @author kay
 * @version 2017-05-04
 */
public class SmsHistory extends DataEntity<SmsHistory> {

    private static final long serialVersionUID = 1L;
    private User user;        // 请求人
    private String ip;        // 请求IP
    private String mobile;        // 发送电话
    private String message;        // 详情

    public SmsHistory() {
        super();
    }

    public SmsHistory(String id) {
        super(id);
    }

    public SmsHistory(String phone, String ipAddress) {
        this.ip = ipAddress;
        this.mobile = phone;
    }

    public SmsHistory(String ip, String mobile, Integer code) {
        this.mobile = mobile;
        this.message = code.toString();
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min = 0, max = 255, message = "请求IP长度必须介于 0 和 255 之间")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Length(min = 0, max = 16, message = "发送电话长度必须介于 0 和 16 之间")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Length(min = 0, max = 255, message = "详情长度必须介于 0 和 255 之间")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}