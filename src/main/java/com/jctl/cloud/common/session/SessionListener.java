package com.jctl.cloud.common.session;

import com.jctl.cloud.modules.sys.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/9/11.
 */
public class SessionListener implements HttpSessionListener {
  private static Logger log=Logger.getLogger(String.valueOf(SessionListener.class));
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session=se.getSession();
        String sessionId=session.getId();
        //在session销毁的时候，把loginUserMap中保存的键值对清除
        User user= (User) session.getAttribute("now_user");
        if(user!=null){
            Map<String,String> loginUserMap=(Map<String,String>) se.getSession();
            if(loginUserMap.get(user.getLoginName()).equals(sessionId)){
                log.info("clean user from application :"+user.getLoginName());
                loginUserMap.remove(user.getLoginName());
                se.getSession().getServletContext().setAttribute("loginUserMap",loginUserMap);
            }
        }
    }
}
