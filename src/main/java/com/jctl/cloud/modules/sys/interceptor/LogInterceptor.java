/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.modules.sys.interceptor;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jctl.cloud.common.service.BaseService;
import com.jctl.cloud.common.utils.DateUtils;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.LogUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 日志拦截器
 * @author ThinkGem
 * @version 2014-8-19
 */
public class LogInterceptor extends BaseService implements HandlerInterceptor {

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
	/*	HttpSession session=request.getSession();
		User user= (User) session.getAttribute("now_user");
		if(session.getAttribute("now_user")==null){
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		//多用户登录限制判断，并给出提示信息
		boolean isLogin=false;
		if(user!=null){
			Map<String,String> loginUserMap=(Map<String,String>)session.getServletContext().getAttribute("loginUserMap");
			String sessionId=session.getId();
			for(String key:loginUserMap.keySet()){
				//用户已在另一处登录
				if(key.equals(user.getLoginName())&&!loginUserMap.containsValue(sessionId)){
					isLogin=true;
					break;
				}
			}
		}
		if(isLogin){
			Map<String,String> loginOutTime=(Map<String,String>)session.getServletContext().getAttribute("loginOutTime");
			session.setAttribute("mess","用户："+user.getLoginName()+"，于"+loginOutTime.get(user.getLoginName())+"已在别处登录");
		}*/
		if (logger.isDebugEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
//			logger.info("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {

		// 保存日志
		LogUtils.saveLog(request, handler, ex, null);
		
		// 打印JVM信息。
//		if (logger.isDebugEnabled()){
//			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
//			long endTime = System.currentTimeMillis(); 	//2、结束时间
//	        logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
//	        		new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
//					request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024,
//					(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
//	        //删除线程变量中的数据，防止内存泄漏
//	        startTimeThreadLocal.remove();
//		}
		
	}

}
