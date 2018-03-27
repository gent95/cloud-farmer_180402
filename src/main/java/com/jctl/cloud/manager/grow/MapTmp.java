package com.jctl.cloud.manager.grow;

import java.io.Serializable;

/**
 * Created by gent on 2017/5/23.
 */
public class MapTmp implements Serializable{
    private String name;
    private  Double value;

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
