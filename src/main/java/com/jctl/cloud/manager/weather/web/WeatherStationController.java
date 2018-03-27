/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.weather.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.manager.weather.entity.Weather;
import com.jctl.cloud.manager.weather.service.WeatherService;

/**
 * 气象站数据Controller
 * @author ll
 * @version 2017-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/weather/weather")
public class WeatherStationController extends BaseController {

	@Autowired
	private WeatherService weatherService;
	
	@ModelAttribute
	public Weather get(@RequestParam(required=false) String id) {
		Weather entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weatherService.get(id);
		}
		if (entity == null){
			entity = new Weather();
		}
		return entity;
	}
	
	@RequiresPermissions("weather:weather:view")
	@RequestMapping(value = {"list", ""})
	public String list(Weather weather, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			Page<Weather> page = weatherService.findPage(new Page<Weather>(request, response), weather);
			model.addAttribute("page", page);
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return "manager/weather/weatherList";
	}

	@RequiresPermissions("weather:weather:view")
	@RequestMapping(value = "form")
	public String form(Weather weather, Model model) {
		try{
			model.addAttribute("weather", weather);
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return "manager/weather/weatherForm";
	}

	@RequiresPermissions("weather:weather:edit")
	@RequestMapping(value = "save")
	public String save(Weather weather, Model model, RedirectAttributes redirectAttributes) {
        try{
            if (!beanValidator(model, weather)){
                    return form(weather, model);
                }
                weatherService.save(weather);
                addMessage(redirectAttributes, "保存气象站数据成功");
            }catch(Exception e){
                addMessage(redirectAttributes, "保存气象站数据失败");
                e.printStackTrace();
            }
		return "redirect:"+Global.getAdminPath()+"/weather/weather/?repage";
	}
	
	@RequiresPermissions("weather:weather:edit")
	@RequestMapping(value = "delete")
	public String delete(Weather weather, RedirectAttributes redirectAttributes) {
	    try{
            weatherService.delete(weather);
            addMessage(redirectAttributes, "删除气象站数据成功");
		}catch(Exception e){
		    addMessage(redirectAttributes, "删除气象站数据失败");
		 	e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/weather/weather/?repage";
	}

}