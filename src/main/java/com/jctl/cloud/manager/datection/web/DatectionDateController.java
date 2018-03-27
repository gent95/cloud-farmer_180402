/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.datection.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.manager.datection.entity.Datection;
import com.jctl.cloud.manager.grow.service.WeatherStationService;
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
import com.jctl.cloud.manager.datection.entity.DatectionDate;
import com.jctl.cloud.manager.datection.service.DatectionDateService;

/**
 * 设备数据Controller
 * @author mjt
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/datection/datectionDate")
public class DatectionDateController extends BaseController {

	@Autowired
	private DatectionDateService datectionDateService;
	
	@ModelAttribute
	public DatectionDate get(@RequestParam(required=false) String id) {
		DatectionDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = datectionDateService.get(id);
		}
		if (entity == null){
			entity = new DatectionDate();
		}
		return entity;
	}
	
	@RequiresPermissions("datection:datectionDate:view")
	@RequestMapping(value = {"list", ""})
	public String list(DatectionDate datectionDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DatectionDate> page = datectionDateService.findPage(new Page<DatectionDate>(request, response), datectionDate);
		model.addAttribute("page", page);
		return "manager/datection/datectionDateList";
	}

	@RequiresPermissions("datection:datectionDate:view")
	@RequestMapping(value = "form")
	public String form(DatectionDate datectionDate, Model model) {
		model.addAttribute("datectionDate", datectionDate);
		return "manager/datection/datectionDateForm";
	}

	@RequiresPermissions("datection:datectionDate:edit")
	@RequestMapping(value = "save")
	public String save(DatectionDate datectionDate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, datectionDate)){
			return form(datectionDate, model);
		}
		datectionDateService.save(datectionDate);
		addMessage(redirectAttributes, "保存设备数据成功");
		return "redirect:"+Global.getAdminPath()+"/datection/datectionDate/?repage";
	}
	
	@RequiresPermissions("datection:datectionDate:edit")
	@RequestMapping(value = "delete")
	public String delete(DatectionDate datectionDate, RedirectAttributes redirectAttributes) {
		datectionDateService.delete(datectionDate);
		addMessage(redirectAttributes, "删除设备数据成功");
		return "redirect:"+Global.getAdminPath()+"/datection/datectionDate/?repage";
	}

}