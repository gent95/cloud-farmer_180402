package com.jctl.cloud.utils;

import com.jctl.cloud.common.entity.LoginResult;
import com.jctl.cloud.common.utils.CacheUtils;
import com.jctl.cloud.common.utils.IdGen;
import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.modules.sys.dao.RoleDao;
import com.jctl.cloud.modules.sys.dao.UserDao;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lewKay on 2017/3/23.
 */
public class FrontUserUtils {


    public static final String API_USER_CACHE = "apiUserCache";
    public static final String SINGLE_ID = "singleId";

    private static UserService userService = SpringContextHolder.getBean(UserService.class);

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);

    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);


    public static LoginResult doLogin(User loginUser, HttpServletRequest request) {

        User user = userService.getByLoginName(loginUser);
        if (user == null) {
            return LoginResult.登录失败;
        }
        if (user.getLoginFlag() == null || user.getLoginFlag().equals("0")) {
            return LoginResult.用户被锁定;
        }
        Boolean pass = SystemService.validatePassword(loginUser.getPassword(), user.getPassword());
        if (pass) {
            if (loginUser.getChannelId() != null && !loginUser.getChannelId().equals("")) {
                user.setChannelId(loginUser.getChannelId());
                userService.updateChannelId(user);
            }
            //唯一登陆标示
            updateUserSingle(request, user);

            return LoginResult.登录成功;
        }
        return LoginResult.登录失败;
    }

    public static void updateUserSingle(HttpServletRequest request, User user) {
        FrontUserUtils.cleanCache(user);
        user.setSingleId(IdGen.uuid());
        user.setLoginDate(new Date());
        userService.update(user);
        request.getSession().setAttribute("user", user);
        setUser(user);//放入缓存
    }

//    /**
//     * 通过唯一识别码登陆
//     *
//     * @param request
//     * @return
//     */
//    public static User getUser(HttpServletRequest request) {
//        String singleId = request.getParameter("singleId");
//        User user = (User) CacheUtils.get(API_USER_CACHE, singleId);
//        if (user == null) {
//            user = userDao.getBySingleId(singleId);
//            if (user == null) {
//                return null;
//            }
//            user.setRoleList(roleDao.findList(new Role(user)));
//            CacheUtils.put(API_USER_CACHE, singleId, user);
//            return user;
//        }
//        return user;
//    }

    /**
     * 通过唯一识别码登陆
     *
     * @return
     */
    public static User getUser() {
        String singleId = "";
        try {
            if (RequestContextHolder.getRequestAttributes() == null) {
                return new User();
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            singleId = request.getParameter(SINGLE_ID);
            if (singleId == null || singleId.equals("")) {
                return new User();
            }
            User user = (User) CacheUtils.get(API_USER_CACHE, singleId);
            if (user == null) {
                user = userDao.getBySingleId(singleId);
                if (user == null) {
                    return new User();
                }
                user.setRoleList(roleDao.findList(new Role(user)));
                CacheUtils.put(API_USER_CACHE, singleId, user);
                return user;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }

    /**
     * 登陆时调用一次
     *
     * @return
     */
    public static User getUserBySession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (User) request.getSession().getAttribute("user");
    }

    public static void cleanCache(User user) {
        CacheUtils.remove(API_USER_CACHE, user.getSingleId());
        user.setSingleId("");
        userDao.update(user);
    }


    public static void setUser(User user) {
        user.setRoleList(roleDao.findList(new Role(user)));
        CacheUtils.put(API_USER_CACHE, user.getSingleId(), user);
    }

    public static boolean isLogin() {
        boolean flag = false;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.getId() != null && !user.getId().equals("")) {
            return true;
        }
        return flag;

    }


    public static void cleanCache() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = getUser();
        user.setSingleId("");
        user.setChannelId("");
        userDao.update(user);
        String singleId = request.getParameter(SINGLE_ID);
        CacheUtils.remove(API_USER_CACHE, singleId);
    }

    public static User getUser(String singleId) {
        User user = (User) CacheUtils.get(API_USER_CACHE, singleId);
        if (user == null) {
            user = userDao.getBySingleId(singleId);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            return user;
        }
        return user;
    }
}
