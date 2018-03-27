package com.jctl.cloud.api.common;

import com.jctl.cloud.app.banner.entity.AppBanner;
import com.jctl.cloud.app.banner.service.AppBannerService;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.news.entity.News;
import com.jctl.cloud.manager.news.service.INewsService;
import com.jctl.cloud.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KAY on 2017/4/21.
 */

@Controller
@RequestMapping("common")
public class ACommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private INewsService newsService;
    @Autowired
    private AppBannerService appBannerService;

    @RequestMapping("banner")
    @ResponseBody
    public Map list() {
        Map result = new HashMap();
        List data = new ArrayList();
        try {
            List<AppBanner> list = appBannerService.findList(new AppBanner());
            String[] propertys = new String[]{"path", "url"};

            for (AppBanner banner : list) {
                Map map = new HashMap();
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(banner, property));
                }
                data.add(map);
            }
            result.put("flag", 1);
            result.put("info", data);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "index")
    public Map<String, Object> index(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> banners = new ArrayList<>();
        try {
            List<AppBanner> list = appBannerService.findList(new AppBanner());

            String[] propertys = new String[]{"path", "url"};
            for (AppBanner banner : list) {
                Map map = new HashMap();
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(banner, property));
                }
                banners.add(map);
            }
            result.put("banner", banners);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List newsList = new ArrayList();
        try {
            List<News> newList = newsService.newsData();
            if (newList != null) {
                String[] propertys1 = new String[]{"id", "title", "content", "dataTime", "url"};

                for (News news : newList) {
                    Map maps = new HashMap();
                    for (String property : propertys1) {
                        maps.put(property, Reflections.invokeGetter(news, property));
                    }
                    newsList.add(maps);
                }
                result.put("news", newsList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newsList.size() == 0 && banners.size() == 0) {
            result.put("flag", 0);
            result.put("msg", "加载失败！");
            return result;
        }
        result.put("flag", 1);
        return result;
    }
}
