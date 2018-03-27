package com.jctl.cloud.common.session;

import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.UserService;
import freemarker.template.utility.DateUtil;
import com.jctl.cloud.common.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Detail;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/9/11.
 */
@Service
@Transactional
public class LimiteLogin {
    private static Logger log= Logger.getLogger(String.valueOf(SessionListener.class));
    private static Map<String,String> loginUserMap=new HashMap<>();//存储在线用户
    private static Map<String,String> loginOutTime=new HashMap<>();//存储踢出用户
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   @Autowired
    private UserService userService;
   public  String loginLimite(HttpServletRequest request,String userName){
       User uname=new User();
       uname.setLoginName(userName);
       User user=userService.getByLoginName(uname);
       String sessionId=request.getSession().getId();
       for(String key :loginUserMap.keySet()){
        //用户已在另一处登录
           if(key.equals(user.getLoginName())&&!loginUserMap.containsValue(sessionId)){
               log.info("用户"+user.getLoginName()+"，于"+sdf.format(new Date())+"被踢出！");
                loginOutTime.put(user.getLoginName(),sdf.format(new Date()));
                loginUserMap.remove(user.getLoginName());
                break;
           }
       }
       loginUserMap.put(user.getLoginName(),sessionId);
       request.getSession().getServletContext().setAttribute("loginUserMap",loginUserMap);
        request.getSession().getServletContext().setAttribute("loginOutTime",loginOutTime);
        return "success";
   }
}
