/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.weather.service;

import java.util.List;

import com.jctl.cloud.mina.entity.ResultSetTcp;
import com.jctl.cloud.mina.entity.WeatherResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.weather.entity.Weather;
import com.jctl.cloud.manager.weather.dao.WeatherDao;

/**
 * 气象站数据Service
 * @author ll
 * @version 2017-12-12
 */
@Service
@Transactional(readOnly = true)
public class WeatherService extends CrudService<WeatherDao, Weather> {

	@Override
	public Weather get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Weather> findList(Weather weather) {
		return super.findList(weather);
	}

	@Override
	public Page<Weather> findPage(Page<Weather> page, Weather weather) {
		return super.findPage(page, weather);
	}
	@Autowired
	private WeatherDao weatherDao;
	@Override
	@Transactional(readOnly = false)
	public void save(Weather weather) {
		super.save(weather);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Weather weather) {
		super.delete(weather);
	}
	@Transactional(readOnly = false)






	public void saveOrUpdate(ResultSetTcp resultSetTcp){
		Weather weather=new Weather();
		if(resultSetTcp.getWeatherResultSet()!=null){
			WeatherResultSet weatherResultSet=resultSetTcp.getWeatherResultSet();
			if(weatherResultSet.getNumberId()!=null){
				weather.setNumberId(weatherResultSet.getNumberId());
			}
			if(weatherResultSet.getAddTime()!=null){
				weather.setAddTime(weatherResultSet.getAddTime());
			}
			if(weatherResultSet.getWindSpeed()!=null){
				weather.setWindSpeed(weatherResultSet.getWindSpeed());
			}
			if(weatherResultSet.getWindDirection()!=null){
				weather.setWindDirection(weatherResultSet.getWindDirection());
			}
			if(weatherResultSet.getRainfall()!=null){
				weather.setRainfall(weatherResultSet.getRainfall());
			}
			if(weatherResultSet.getTemperature()!=null){
				weather.setTemperature(weatherResultSet.getTemperature());
			}
			if(weatherResultSet.getHumidity()!=null){
				weather.setHumidity(weatherResultSet.getHumidity());
			}
			if(weatherResultSet.getAirPressure()!=null){
				weather.setAirPressure(weatherResultSet.getAirPressure());
			}
			if(weatherResultSet.getCo2()!=null){
				weather.setCo2(weatherResultSet.getCo2());
			}
			if(weatherResultSet.getPhotosynthetically()!=null){
				weather.setPhotosynthetically(weatherResultSet.getPhotosynthetically());
			}
			if(weatherResultSet.getSunlightDuration()!=null){
				weather.setSunlightDuration(weatherResultSet.getSunlightDuration());
			}
			if(weatherResultSet.getRadiate()!=null){
				weather.setRadiate(weatherResultSet.getRadiate());
			}
			if(weatherResultSet.getEvaporationCapacity()!=null){
				weather.setEvaporationCapacity(weatherResultSet.getEvaporationCapacity());
			}
			if(weatherResultSet.getVoltage()!=null){
				weather.setVoltage(weatherResultSet.getVoltage());
			}
			if(weatherResultSet.getSoilTemperature10()!=null){
				weather.setSoilTemperature10(weatherResultSet.getSoilTemperature10());
			}
			if(weatherResultSet.getSoilHumidity10()!=null){
				weather.setSoilHumidity10(weatherResultSet.getSoilHumidity10());
			}
			if(weatherResultSet.getSoilTemperature20()!=null){
				weather.setSoilTemperature20(weatherResultSet.getSoilTemperature20());
			}
			if(weatherResultSet.getSoilHumidity20()!=null){
				weather.setSoilHumidity20(weatherResultSet.getSoilHumidity20());
			}
			if(weatherResultSet.getSoilTemperature30()!=null){
				weather.setSoilTemperature30(weatherResultSet.getSoilTemperature30());
			}
			if(weatherResultSet.getSoilHumidity30()!=null){
				weather.setSoilHumidity30(weatherResultSet.getSoilHumidity30());
			}
			if(weatherResultSet.getSoilTemperature40()!=null){
				weather.setSoilTemperature40(weatherResultSet.getSoilTemperature40());
			}
			if(weatherResultSet.getSoilHumidity40()!=null){
				weather.setSoilHumidity40(weatherResultSet.getSoilHumidity40());
			}
			if(weatherResultSet.getSoilTemperature50()!=null){
				weather.setSoilTemperature50(weatherResultSet.getSoilTemperature50());
			}
			if(weatherResultSet.getSoilHumidity50()!=null){
				weather.setSoilHumidity50(weatherResultSet.getSoilHumidity50());
			}
			if(weatherResultSet.getSoilTemperature60()!=null){
				weather.setSoilTemperature60(weatherResultSet.getSoilTemperature60());
			}
			if(weatherResultSet.getSoilHumidity60()!=null){
			weather.setSoilHumidity60(weatherResultSet.getSoilHumidity60());
			}
			save(weather);
		}
	}

	public List<Weather> getTop8Data(){
		return weatherDao.findTop8Data();
	}
}