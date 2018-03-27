/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.weather.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 气象站数据Entity
 * @author ll
 * @version 2017-12-12
 */
public class Weather extends DataEntity<Weather> {
	
	private static final long serialVersionUID = 1L;
	private Long numberId;		// 设备编号
	private Date addTime;		// 日期时间
	private String windSpeed;		// 风速
	private String windDirection;		// 风向
	private String rainfall;		// 雨量
	private String temperature;		// 温度
	private String humidity;		// 湿度
	private String airPressure;		// 气压
	private String co2;		// 二氧化碳浓度
	private String photosynthetically;		// 光合有效辐射
	private String sunlightDuration;		// 日照时数
	private String radiate;		// 总辐射
	private String evaporationCapacity;		// 蒸发量
	private String voltage;		// 电压
	private String soilTemperature10;		// 10cm土壤温度
	private String soilHumidity10;		// 10cm土壤湿度
	private String soilTemperature20;		// 20cm土壤温度
	private String soilHumidity20;		// 20cm土壤湿度
	private String soilTemperature30;		// 30cm土壤温度
	private String soilHumidity30;		// 30cm土壤湿度
	private String soilTemperature40;		// 40cm土壤温度
	private String soilHumidity40;		// 40cm土壤湿度
	private String soilTemperature50;		// 50cm土壤温度
	private String soilHumidity50;		// 50cm土壤湿度
	private String soilTemperature60;		// 60cm土壤温度
	private String soilHumidity60;		// 60cm土壤湿度
	private String addUserId;		// 添加人
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	
	public Weather() {
		super();
	}

	public Weather(String id){
		super(id);
	}

	public Long getNumberId() {
		return numberId;
	}

	public void setNumberId(Long numberId) {
		this.numberId = numberId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	
	public String getRainfall() {
		return rainfall;
	}

	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}
	
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	public String getAirPressure() {
		return airPressure;
	}

	public void setAirPressure(String airPressure) {
		this.airPressure = airPressure;
	}
	
	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}
	
	public String getPhotosynthetically() {
		return photosynthetically;
	}

	public void setPhotosynthetically(String photosynthetically) {
		this.photosynthetically = photosynthetically;
	}
	
	public String getSunlightDuration() {
		return sunlightDuration;
	}

	public void setSunlightDuration(String sunlightDuration) {
		this.sunlightDuration = sunlightDuration;
	}
	
	public String getRadiate() {
		return radiate;
	}

	public void setRadiate(String radiate) {
		this.radiate = radiate;
	}
	
	public String getEvaporationCapacity() {
		return evaporationCapacity;
	}

	public void setEvaporationCapacity(String evaporationCapacity) {
		this.evaporationCapacity = evaporationCapacity;
	}
	
	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	
	public String getSoilTemperature10() {
		return soilTemperature10;
	}

	public void setSoilTemperature10(String soilTemperature10) {
		this.soilTemperature10 = soilTemperature10;
	}
	
	public String getSoilHumidity10() {
		return soilHumidity10;
	}

	public void setSoilHumidity10(String soilHumidity10) {
		this.soilHumidity10 = soilHumidity10;
	}
	
	public String getSoilTemperature20() {
		return soilTemperature20;
	}

	public void setSoilTemperature20(String soilTemperature20) {
		this.soilTemperature20 = soilTemperature20;
	}
	
	public String getSoilHumidity20() {
		return soilHumidity20;
	}

	public void setSoilHumidity20(String soilHumidity20) {
		this.soilHumidity20 = soilHumidity20;
	}
	
	public String getSoilTemperature30() {
		return soilTemperature30;
	}

	public void setSoilTemperature30(String soilTemperature30) {
		this.soilTemperature30 = soilTemperature30;
	}
	
	public String getSoilHumidity30() {
		return soilHumidity30;
	}

	public void setSoilHumidity30(String soilHumidity30) {
		this.soilHumidity30 = soilHumidity30;
	}
	
	public String getSoilTemperature40() {
		return soilTemperature40;
	}

	public void setSoilTemperature40(String soilTemperature40) {
		this.soilTemperature40 = soilTemperature40;
	}
	
	public String getSoilHumidity40() {
		return soilHumidity40;
	}

	public void setSoilHumidity40(String soilHumidity40) {
		this.soilHumidity40 = soilHumidity40;
	}
	
	public String getSoilTemperature50() {
		return soilTemperature50;
	}

	public void setSoilTemperature50(String soilTemperature50) {
		this.soilTemperature50 = soilTemperature50;
	}
	
	public String getSoilHumidity50() {
		return soilHumidity50;
	}

	public void setSoilHumidity50(String soilHumidity50) {
		this.soilHumidity50 = soilHumidity50;
	}
	
	public String getSoilTemperature60() {
		return soilTemperature60;
	}

	public void setSoilTemperature60(String soilTemperature60) {
		this.soilTemperature60 = soilTemperature60;
	}
	
	public String getSoilHumidity60() {
		return soilHumidity60;
	}

	public void setSoilHumidity60(String soilHumidity60) {
		this.soilHumidity60 = soilHumidity60;
	}
	
	@Length(min=0, max=20, message="添加人长度必须介于 0 和 20 之间")
	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	@Length(min=0, max=20, message="修改人长度必须介于 0 和 20 之间")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}