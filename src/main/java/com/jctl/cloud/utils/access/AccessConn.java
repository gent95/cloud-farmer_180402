package com.jctl.cloud.utils.access;

import com.jctl.cloud.common.utils.DateUtils;
import com.jctl.cloud.manager.datection.entity.DatectionDate;
import com.jctl.cloud.manager.datection.service.DatectionDateService;
import com.jctl.cloud.manager.facility.entity.Facility;
import com.jctl.cloud.manager.facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * access数据库连接工具类
 * @author gent
 *
 */
@Service
@Lazy(false)
public class AccessConn {
	@Autowired
	FacilityService facilityService;
	@Autowired
	DatectionDateService datectionDateService;

//	@Scheduled(cron = "0 */1 * * * ?")
	public void ConnectAccessFile() throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//		String dataSource = "E:/jcsoft/weathserstation/Data/Access/dat.mdb";
		String dataSource = "D:/app/weatherstation/Data/Access/dat.mdb";
		String dbur = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + dataSource;
		Connection conn = DriverManager.getConnection(dbur);
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("SELECT top 1 * from dHistory WHERE  1=1 ORDER BY `time` DESC ");
		Date endTime = null;
		while(rs.next()){
			endTime = DateUtils.parseDate(rs.getString("time"));
		}
		long diff = new Date().getTime()-endTime.getTime();
		if ((diff/1000%3600/60) >= 30){
			rs = stmt.executeQuery("select top 30 * from dHistory where 1=1 AND `time` LIKE  '%:00:00'");
			Set<DatectionDate> datectionDates = new HashSet<>();
			Facility facility = new Facility();
			facility.setId("84");
			facility = facilityService.get(facility);
			if (facility == null){
				facility = new Facility();
				facility.setId("84");
				facility.setName("鹤泉站");
				facilityService.save(facility);
			}
			//复制access数据库到mysql
			while (rs.next()) {
				DatectionDate datectionDate = new DatectionDate();
				datectionDate.setFacId(facility.getId());
				datectionDate.setWindSpeed(rs.getString("e1"));
				datectionDate.setRainV(rs.getString("e2"));
				datectionDate.setWindDirection(rs.getString("e3"));
				datectionDate.setAirTemperature(rs.getString("e4"));
				datectionDate.setHumidity(rs.getString("e5"));
				datectionDate.setRadiate(rs.getString("e6")); //气压
				datectionDate.setEvaporation(rs.getString("e7")); //光照
				datectionDate.setCreateDate(DateUtils.parseDate(rs.getString("time")));
				datectionDates.add(datectionDate);
			}
			for (DatectionDate datectionDate : datectionDates) {
				datectionDateService.save(datectionDate);
			}
		}
		rs.close();
		stmt.close();
		conn.close();
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void cleardb()throws Exception{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//		String dataSource = "E:/jcsoft/weathserstation/Data/Access/dat.mdb";
		String dataSource = "D:/app/weatherstation/Data/Access/dat.mdb";
		String dbur = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + dataSource;
		Connection conn = DriverManager.getConnection(dbur);
		Statement stmt = conn.createStatement();
		stmt.execute("delete from dHistory ");
		stmt.close();
		conn.close();
	}
}
