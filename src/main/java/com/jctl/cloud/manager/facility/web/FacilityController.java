/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.facility.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.manager.facility.entity.Facility;
import com.jctl.cloud.manager.facility.service.FacilityService;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.access.AccessConn;

/**
 * 气象站设备Controller
 * 
 * @author gent
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/facility/facility")
public class FacilityController extends BaseController {

	@Autowired
	private FacilityService facilityService;
	@Autowired
	private AccessConn accessConn;
	@Value("#{config['farmerBoss']}")
	private String farmerBoss;

	@Value("#{config['farmerWork']}")
	private String farmerWork;
	@Autowired
	private FarmerService farmerService;

	@ModelAttribute
	public Facility get(@RequestParam(required = false) String id) {
		Facility entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = facilityService.get(id);
		}
		if (entity == null) {
			entity = new Facility();
		}
		return entity;
	}

	@RequiresPermissions("facility:facility:view")
	@RequestMapping(value = { "list", "" })
	public String list(Facility facility, HttpServletRequest request, HttpServletResponse response, Model model) {
		 	
		User user = UserUtils.getUser();
		boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
		if (!isAdmin) {
			List<Role> list = UserUtils.getRoleList();
			for (Role role : list) {
				if (role.getEnname().equals(farmerBoss)) {
					facility.setCreateBy(user);
				}
			}
		}
		Page<Facility> page = facilityService.findPage(new Page<Facility>(request, response), facility);
		model.addAttribute("page", page);
//		try {
//			accessConn.ConnectAccessFile();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return "manager/facility/facilityList";
	}

	@RequiresPermissions("facility:facility:view")
	@RequestMapping(value = "form")
	public String form(Facility facility, Model model) {
		model.addAttribute("facility", facility);
		Farmer farmer = farmerService.get(facility.getFarmerId());
		model.addAttribute("farmer",farmer);
		return "manager/facility/facilityForm";
	}

	@RequiresPermissions("facility:facility:edit")
	@RequestMapping(value = "save")
	public String save(Facility facility, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, facility)) {
			return form(facility, model);
		}

		facilityService.save(facility);
		addMessage(redirectAttributes, "保存气象站设备成功");
		return "redirect:" + Global.getAdminPath() + "/facility/facility/?repage";
	}

	@RequiresPermissions("facility:facility:edit")
	@RequestMapping(value = "delete")
	public String delete(Facility facility, RedirectAttributes redirectAttributes) {
		facilityService.delete(facility);
		addMessage(redirectAttributes, "删除气象站设备成功");
		return "redirect:" + Global.getAdminPath() + "/facility/facility/?repage";
	}

}