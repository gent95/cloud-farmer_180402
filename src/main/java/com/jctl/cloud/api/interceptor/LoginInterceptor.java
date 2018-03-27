package com.jctl.cloud.api.interceptor;

import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.utils.FrontUserUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * 安卓登陆过滤
 * Created by kay on 2017/5/11.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = FrontUserUtils.getUser();
        if (user == null) {
            returnJson(httpServletResponse);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        e.notifyAll();
    }


    private void returnJson(HttpServletResponse response) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            Map map = new HashMap();
            map.put("flag", 0);
            map.put("msg", "登陆会话已经过期，请重新登陆！");
            JSONObject jsonObject = JSONObject.fromObject(map);
            writer = response.getWriter();
            writer.print(jsonObject);
        } catch (IOException e) {

        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
