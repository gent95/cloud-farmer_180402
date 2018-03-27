package com.jctl.cloud.mina.core;

import com.jctl.cloud.common.utils.CacheUtils;
import com.jctl.cloud.mina.entity.*;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class SerialPortTcp {
   /* private static final Pattern NUMBER_PATTERN =  Pattern.compile("^[0-9]$");*/
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddmmhhss");
    private static Logger log = LoggerFactory.getLogger(CacheUtils.class);
/*    private String bufferLocal = "";
    protected OutputStream mOutputStream = new OutputStream() {

        @Override
        public void write(int b) throws IOException {

        }
    };*/
/*    private InputStream mInputStream ;*/



    public ResultSetTcp comReadData(final String buffer)
    {
        ResultSetTcp resultSetTcp = new ResultSetTcp();
        if(buffer != null)
        //命令
        {
            resultSetTcp.setOrders(buffer);
        }
        //解析命令所返回来的信息
        String[] bufStr=buffer.split(" ");
        System.out.println("size:;;;;;;;;;;;;;;;;"+bufStr.length);
        if(bufStr.length==26){
            WeatherResultSet weatherResultSet=new WeatherResultSet();
            Long nId= Long.valueOf(bufStr[0]);
            weatherResultSet.setNumberId(nId);
            try {
                weatherResultSet.setAddTime(sdf.parse(bufStr[1]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            weatherResultSet.setWindSpeed(analyDate(bufStr[2]));

            int windDirection= Integer.parseInt(bufStr[3]);
            if(windDirection==999){
                windDirection=0;
            }
            weatherResultSet.setWindDirection(String.valueOf(windDirection));

            int rainfall= Integer.parseInt(bufStr[4]);
            weatherResultSet.setRainfall(String.valueOf(rainfall));

            weatherResultSet.setTemperature(analyDate(bufStr[5]));

            int humidity= Integer.parseInt(bufStr[6]);
            if(humidity==999){
                humidity=0;
            }
            weatherResultSet.setHumidity(String.valueOf(humidity));

            int airPressure= Integer.parseInt(bufStr[7]);
            weatherResultSet.setAirPressure(String.valueOf(airPressure));

            int Co2= Integer.parseInt(bufStr[8]);
            weatherResultSet.setCo2(String.valueOf(Co2));

            int photosynthetically= Integer.parseInt(bufStr[9]);
            if(photosynthetically==999){
               photosynthetically=0;
            }
            weatherResultSet.setPhotosynthetically(String.valueOf(photosynthetically));

            int sunlightDuration=Integer.parseInt(bufStr[10]);
            weatherResultSet.setSunlightDuration(String.valueOf(sunlightDuration));

            int radiate=Integer.parseInt(bufStr[11]);
            if(radiate==999){
                radiate=0;
            }
            weatherResultSet.setRadiate(String.valueOf(radiate));

            int evaporationCapacity= Integer.parseInt(bufStr[12]);
            weatherResultSet.setEvaporationCapacity(String.valueOf(evaporationCapacity));

            int voltage= Integer.parseInt(bufStr[13])/10;
            weatherResultSet.setVoltage(String.valueOf(voltage));

            weatherResultSet.setSoilTemperature10(analyDate(bufStr[14]));
            weatherResultSet.setSoilHumidity10(analyDate(bufStr[15]));
            weatherResultSet.setSoilTemperature20(analyDate(bufStr[16]));
            weatherResultSet.setSoilHumidity20(analyDate(bufStr[17]));
            weatherResultSet.setSoilTemperature30(analyDate(bufStr[18]));
            weatherResultSet.setSoilHumidity30(analyDate(bufStr[19]));
            weatherResultSet.setSoilTemperature40(analyDate(bufStr[20]));
            weatherResultSet.setSoilHumidity40(analyDate(bufStr[21]));
            weatherResultSet.setSoilTemperature50(analyDate(bufStr[22]));
            weatherResultSet.setSoilHumidity50(analyDate(bufStr[23]));
            weatherResultSet.setSoilTemperature60(analyDate(bufStr[24]));
            weatherResultSet.setSoilHumidity60(analyDate(bufStr[25]));
            resultSetTcp.setWeatherResultSet(weatherResultSet);
        }else {
            log.info("接收到的数据错误！！！");
        }
        return resultSetTcp;
    }

    public String  analyDate(String data){
        Double t=Double.valueOf(data);
        if(t==999){
            t=0.0;
        }
      Double text= t/10;
        return text.toString();
    }

    /*private boolean isNumber(String str){
        return NUMBER_PATTERN.matcher(str).matches();
    }*/
}