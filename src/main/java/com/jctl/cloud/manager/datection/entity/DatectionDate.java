/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.datection.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

import java.util.Date;

/**
 * 设备数据Entity
 * @author mjt
 * @version 2017-05-04
 */
public class DatectionDate extends DataEntity<DatectionDate> {
	
	private static final long serialVersionUID = 1L;
	private String facId;		// 历史数据
	private String windSpeed;		// 风速
	private String airTemperature;		// 气温
	private String humidity;		// 湿度
	private String rainV;		// 雨量
	private String radiate;		// 气压
	private String windDirection;		// 风向
	private String evaporation;		// 光照
	protected Date createDate;    // 创建日期

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public DatectionDate() {
		super();
	}

	public DatectionDate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="历史数据长度必须介于 0 和 64 之间")
	public String getFacId() {
		return facId;
	}

	public void setFacId(String facId) {
		this.facId = facId;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	public String getAirTemperature() {
		return airTemperature;
	}

	public void setAirTemperature(String airTemperature) {
		this.airTemperature = airTemperature;
	}
	
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	public String getRainV() {
		return rainV;
	}

	public void setRainV(String rainV) {
		this.rainV = rainV;
	}
	
	public String getRadiate() {
		return radiate;
	}

	public void setRadiate(String radiate) {
		this.radiate = radiate;
	}
	
	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	
	public String getEvaporation() {
		return evaporation;
	}

	public void setEvaporation(String evaporation) {
		this.evaporation = evaporation;
	}
	
}