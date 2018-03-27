package com.jctl.cloud.manager.grow.service;

import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.common.utils.DateUtils;
import com.jctl.cloud.manager.datection.dao.DatectionDateDao;
import com.jctl.cloud.manager.datection.entity.Datection;
import com.jctl.cloud.manager.datection.entity.DatectionDate;
import com.jctl.cloud.manager.datection.service.DatectionDateService;
import com.jctl.cloud.utils.sqlServer.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gent on 2017/5/25.
 */
//@Service
//@Lazy(false)
public class WeatherStationService{
   private Connection connection =  ConnectionUtil.getConnection();
   private Statement stmt = null;

   @Autowired
   private DatectionDateService datectionDateService;

    /**
     * 获取八条整点数据
     * @return
     * @throws Exception
     */
    public List<Datection> getData()throws Exception {
      return get("select top 8 * from dHistory where datepart(mi,time) = 0 and datepart(ss,time) = 0 ORDER BY time DESC");
    }

    /**
     * 每天零点是清除非整点数据
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteData(){
        try{
            stmt = connection.prepareCall("DELETE FROM dHistory WHERE NOT datepart(mi,time) = 0 AND datepart(ss,time) = 0");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            ConnectionUtil.close(null,stmt,connection);
        }
    }

    /**
     * 获取所有数据
     * @return
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void getAll(){
        List<Datection> list = null;
        try{
          list =  get("select * from dHistory ");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            datectionDateService.delete(new DatectionDate());
            for (Datection dd: list) {
                DatectionDate datectionDate = new DatectionDate();
                datectionDate.setCreateDate(DateUtils.parseDate(dd.getCreateDate()));
                datectionDate.setAirTemperature(dd.getAirTemperature());
                datectionDate.setEvaporation(dd.getEvaporation());
                datectionDate.setFacId(dd.getFacId());
                datectionDate.setHumidity(dd.getHumidity());
                datectionDate.setRadiate(dd.getRadiate());
                datectionDate.setRainV(dd.getRainV());
                datectionDateService.save(datectionDate);
            }
        }


    }

    public List<Datection> get(String sql){
        ResultSet rs = null;
        List<Datection> list = new ArrayList<>();
        try{
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Datection datection = new Datection();
                datection.setWindSpeed(rs.getString("e1"));
                datection.setRainV(rs.getString("e2"));
                datection.setWindDirection(rs.getString("e3"));
                datection.setAirTemperature(rs.getString("e4"));
                datection.setHumidity(rs.getString("e5"));
                datection.setRadiate(rs.getString("e6")); //气压
                datection.setEvaporation(rs.getString("e7")); //光照
                datection.setCreateDate(rs.getString("time").replace(".0",""));
                list.add(datection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            ConnectionUtil.close(rs,stmt,connection);
            return list;
        }
    }
}
