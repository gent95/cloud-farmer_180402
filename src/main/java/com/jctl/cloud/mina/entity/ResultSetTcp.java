package com.jctl.cloud.mina.entity;

/**
 * 返回结果类
 *
 */
public class ResultSetTcp {
	private WeatherResultSet weatherResultSet;
	
	private String orders;

	public WeatherResultSet getWeatherResultSet() {
		return weatherResultSet;
	}

	public void setWeatherResultSet(WeatherResultSet weatherResultSet) {
		this.weatherResultSet = weatherResultSet;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "处理结果--> [\n气象站数据=" + weatherResultSet
				+ ",\n 命令=" + orders+ "\n]";
	}
	
	

}
