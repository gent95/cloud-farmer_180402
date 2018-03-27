package com.jctl.cloud.common.utils.http;

/**
 * Created by kay on 2016/12/22 0022.
 */
public class UploadResult {

    private String ipAddr;
    private String url;
    private String picName;

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
