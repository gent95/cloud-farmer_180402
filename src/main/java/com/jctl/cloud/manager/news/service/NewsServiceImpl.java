package com.jctl.cloud.manager.news.service;

import com.jctl.cloud.common.config.JCTLConfig;
import com.jctl.cloud.common.utils.CacheUtils;
import com.jctl.cloud.manager.news.entity.News;
import com.jctl.cloud.utils.jsoup.JsoupUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjt，kay on 2017/3/27.
 */
@Service
public class NewsServiceImpl implements INewsService, JCTLConfig {


    /**
     * 获取新闻列表
     *
     * @return
     */
    public List<News> newsData() {
        List<News> list = (List<News>) CacheUtils.get(NEWS);
        if (list != null && list.size() > 0) {
            return list;
        }
        //增加缓存
        list = getElement();
        if (list != null && list.size() != 0) {
            CacheUtils.put("news", list);
        }
        return list;
    }


    public List<News> getElement() {
        List<News> lists = new ArrayList<>();
        Document doc = JsoupUtils.getDocument(BURL);
        if (doc == null) {
            return new ArrayList<>();
        }
        Elements divList = doc.select("body table:eq(1) tbody tr td:eq(1) table tbody tr:eq(2) td table tbody");
        Elements context = divList.select("tr");
        for (int i = 0; i < context.size(); i++) {
            News news = new News();
            news.setId(i + 1);
            news.setTitle(divList.select("tr:eq(" + i + ") td:eq(1) a").text());
            news.setDataTime(divList.select("tr:eq(" + i + ") td:eq(2)").text());
            String ulrs = divList.select("tr:eq(" + i + ") td:eq(1) a").attr("href");
            ulrs = ulrs.substring(1, ulrs.length());
            news.setUrl("http://pfsc.agri.cn/scdt" + ulrs);
            Document document = JsoupUtils.getDocument(news.getUrl());
            Elements con_top = document.getElementsByClass("cont_midtop");
            String content = con_top.select("table:eq(1) tbody tr:eq(1) td div").text();
            news.setContent(content);
            lists.add(news);
        }
        return lists;
    }


    public static void main(String[] args) {


    }
}
