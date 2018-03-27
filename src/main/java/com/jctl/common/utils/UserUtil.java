package com.jctl.common.utils;

import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.common.entity.RoleEnum;

import java.util.List;

/**
 * 用户工具
 */
public class UserUtil {




    public static RoleEnum getUserRoleType(User user){
        return caseRoleType(user);
    }
    public static RoleEnum getUserRoleType(){
        return caseRoleType(UserUtils.getUser());
    }

    public static RoleEnum getUserRoleType(String id){

        return caseRoleType(UserUtils.get(id));
    }

    private static RoleEnum caseRoleType(User user) {
        if (user.isAdmin()) {
            return RoleEnum.dept;
        }
        List<Role> roles = user.getRoleList();

        if (roles.isEmpty()) {
            roles = UserUtils.getRoleList();
        }
        for (Role role : roles) {
            switch (RoleEnum.getRoleEName(role.getEnname())) {
                case farmerBoss:
                    return RoleEnum.farmerBoss;
                case farmerWorker:
                    return RoleEnum.farmerWorker;
                case expert:
                    return RoleEnum.expert;
                case app_manager:
                    return RoleEnum.app_manager;
                case regulator:
                    return RoleEnum.regulator;
            }
        }
        return null;
    }
}
