package com.jctl.cloud.test;

import com.jctl.cloud.manager.message.service.WaringMessageService;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/9/7.
 */
public class Test1 {

    @Autowired
    private WaringMessageService waringMessageService;
    public static void main(String[] args) throws NoSuchMethodException {
        Class clz = NodeDataDetails.class;
        Field[] fields = clz.getDeclaredFields();
        for (Field field:fields) {
            StringBuffer sb = new StringBuffer();
            sb.append("get");
            sb.append(field.getName().substring(0,1).toUpperCase());
            sb.append(field.getName().substring(1));
            System.out.println(sb);
        }
//        Method methods = clz.getDeclaredMethod("getSoilHumidity2");
//        System.out.println(methods.getName());
    }


}
