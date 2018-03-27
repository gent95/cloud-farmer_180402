package com.jctl.cloud.manager.msgsend.iputil;

import com.alibaba.druid.support.http.util.IPAddress;

import java.net.InetAddress;

/**
 * Created by Administrator on 2017/4/26.
 */
public class IpUtil {
    public  final  static  String getIp(){
        String  localip=null;
        try {
            InetAddress ia = InetAddress.getLocalHost();
             localip = ia.getHostAddress();
        }catch (Exception e){
            e.printStackTrace();
        }
        return localip;
    }
}
