package com.jctl.cloud.task;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jctl.cloud.common.utils.CacheUtils;
import com.jctl.cloud.manager.news.service.INewsService;

/**
 * 定时从刷新节点信息
 * Created by lewKay on 2017/2/28.
 */

@Service
@Lazy(false)
public class HotNewsTask {


    @Autowired
    private INewsService newsService;

    public HotNewsTask() {
    }

    /**
     * 每日新闻获取
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void collectionRelayAndNodeInfoTask() {
        newsService.newsData();
    }

}
