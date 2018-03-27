package com.jctl.cloud.task.apiUser;

import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 剔除长时间不登录用户
 * Created by kay on 2017/5/11.
 */

@Service
@Lazy(false)
public class ApiUserTimeOut {
    @Autowired
    private UserService userService;
    private Long TIMEOUT=1000*60*60*24*15L;

    /**
     * 每天晚上23点  剔除超出15天未登陆等用户
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void outTimeOutUser() {
        List<User> list = userService.findAllUser();

        for (User user : list) {
            if(user.getLoginDate()!= null){
                if(( System.currentTimeMillis()- user.getLoginDate().getTime())>TIMEOUT){
                    FrontUserUtils.cleanCache(user);
                }
            }
        }

    }








}
