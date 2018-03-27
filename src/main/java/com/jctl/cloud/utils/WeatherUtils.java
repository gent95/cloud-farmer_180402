package com.jctl.cloud.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.region.entity.Region;
import com.jctl.cloud.manager.region.service.RegionService;
import com.jctl.cloud.utils.code.UnicodeUtil;
import com.jctl.cloud.utils.jsoup.HttpUtils;
import org.apache.commons.collections.FastArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by LewKay on 2017/3/17.
 * 天气预报工具类
 */
public class WeatherUtils {
    private static StringBuffer address;


    private final String ZHIXINKEY = "mbgnsanngysl3nxl";//知心

    /**
     * 根据城市编号获取天气信息
     *
     * @param cityId
     * @return
     */
    public static Map getWeather(String cityId) {
        Map map;
        address = new StringBuffer("http://www.weather.com.cn/data/sk/");
        address.append(cityId);
        address.append(".html");
        BufferedReader reader;
        try {
            StringBuffer strBuf = new StringBuffer();
            URL url = new URL(address.toString().trim());
            URLConnection conn = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(strBuf.toString().trim(), HashMap.class);
            return (Map) map.get("weatherinfo");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {

        }
        return null;
    }

    /**
     * 根据城市名称获取天气状况
     *
     * @return
     */
    public static Map getWeatherByName(Region search) {
        Map map;
        address = new StringBuffer("https://api.seniverse.com/v3/weather/now.json?key=mbgnsanngysl3nxl&location=");
        if (!search.getPinyin().equals("")) {
            address.append(search.getPinyin());
        } else {
            address.append("beijing");
        }
        BufferedReader reader;
        try {
            StringBuffer strBuf = new StringBuffer();
            URL url = new URL(address.toString().trim());
            URLConnection conn = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(strBuf.toString().trim(), HashMap.class);

            List list = (List) map.get("results");

            map = (Map) list.get(0);
            map = (Map) map.get("now");
            return map;
        } catch (IOException e) {
            map = null;
            e.printStackTrace(System.out);
        } finally {

        }
        return map;
    }

    /**
     * 根据城市名称获取天气状况
     *
     * @return
     */
    public static Map getWeather() {
        Map map = new HashMap();
        String city = ChineseToPinyin.getPingYin(getCity());
        address = new StringBuffer("https://api.seniverse.com/v3/weather/now.json?key=mbgnsanngysl3nxl&location=" + city.trim());
        try {
            String str = HttpUtils.getConnectResult(address.toString());
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(str, HashMap.class);
            List list = (List) map.get("results");
            map = (Map) list.get(0);
            map = (Map) map.get("now");
            return map;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return map;

    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        getWeather();
    }

    /**
     * 新浪接口库
     * @return
     */
    public static String getCity() {
        StringBuffer addr = new StringBuffer("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js");
        try {
            String str = HttpUtils.getConnectResult(addr.toString());
            String[] xx = str.split(",");
            String city = xx[5].split(":")[1];
            city = UnicodeUtil.decodeUnicode(city);
            String newStr = city.replaceAll("\"", "");
            return newStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "北京";
    }

}
