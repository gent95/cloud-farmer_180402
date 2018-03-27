package com.jctl.cloud.utils.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 爬虫util
 * create by gent 2017年3月23日
 */
public class JsoupUtils {


    private static Document document = null;

    /**
     * 通过地址得到document对象
     *
     * @param url 目标网站路径
     * @return
     */
    public static Document getDocument(String url) {

        try {
            document = Jsoup.connect(url).timeout(60).get();
            if (document == null || document.toString().equals("")) {
                getDocument(url);
            }
        } catch (Exception e) {
//            if (request != null) {
//                HttpUtils.setProxyIp(request);
//                document = getDocument(url, request);
//            }
        }
        return document;
    }


    public static void main(String[] args) {


    }
}
