package com.jctl.cloud.manager.datection.entity;


/**
 * Created by gent on 2017/5/26.
 */
public class Datection {
    private static final long serialVersionUID = 1L;
    private String facId;		// 历史数据
    private String windSpeed;		// 风速
    private String airTemperature;		// 气温
    private String humidity;		// 湿度
    private String rainV;		// 雨量
    private String radiate;		// 气压
    private String windDirection;		// 风向
    private String evaporation;		// 光照
    protected String createDate;    // 创建日期

    public void setFacId(String facId) {
        this.facId = facId;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setAirTemperature(String airTemperature) {
        this.airTemperature = airTemperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setRainV(String rainV) {
        this.rainV = rainV;
    }

    public void setRadiate(String radiate) {
        this.radiate = radiate;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setEvaporation(String evaporation) {
        this.evaporation = evaporation;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFacId() {

        return facId;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getAirTemperature() {
        return airTemperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getRainV() {
        return rainV;
    }

    public String getRadiate() {
        return radiate;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getEvaporation() {
        return evaporation;
    }

    public String getCreateDate() {
        return createDate;
    }
}
