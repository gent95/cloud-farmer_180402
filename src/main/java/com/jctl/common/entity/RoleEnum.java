package com.jctl.common.entity;

/**
 * 项目角色英文名约定枚举类
 * 跟随数据库
 * 跟随项目而定
 */
public enum RoleEnum {

    farmerBoss("农场主","farmerBoss"),
    farmerWorker("农户","farmerWorker"),
    regulator("管理者","regulator"),
    dept("系统管理员","dept"),
    app_manager("app管理","app_manager"),
    expert("专家","expert");

    RoleEnum(String s, String s1) {
    }
    public static RoleEnum getRoleEName(String eName) {
        return valueOf(eName);
    }

}
