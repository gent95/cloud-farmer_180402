/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.machine.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
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
import com.jctl.cloud.manager.machine.entity.MachineRecord;
import com.jctl.cloud.manager.machine.service.MachineRecordService;

import java.util.Date;
import java.util.List;

/**
 * 加工记录Controller
 * @author ll
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/machine/machineRecord")
public class MachineRecordController extends BaseController {

	@Autowired
	private MachineRecordService machineRecordService;
	@Autowired
	private FarmlandService farmlandService;
	
	@ModelAttribute
	public MachineRecord get(@RequestParam(required=false) String id) {
		MachineRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = machineRecordService.get(id);
		}
		if (entity == null){
			entity = new MachineRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("machine:machineRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(MachineRecord machineRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean isAdmin= User.isAdmin(UserUtils.getUser().getId());
		if(!isAdmin){
			List<Role> lists=UserUtils.getRoleList();
			for(Role role:lists){
				if(role.getEnname().equals("farmerBoss")){
					machineRecord.setUser(UserUtils.getUser());
				}
				 if(role.getEnname().equals("farmerWork")){
					machineRecord.setUsedId(UserUtils.getUser().getId());
				}
			}
		}
		Page<MachineRecord> page = machineRecordService.findPage(new Page<MachineRecord>(request, response), machineRecord); 
		model.addAttribute("page", page);
		return "manager/machine/machineRecordList";
	}

	@RequiresPermissions("machine:machineRecord:view")
	@RequestMapping(value = "form")
	public String form(MachineRecord machineRecord, Model model) {
		Farmland farmland=new Farmland();
		boolean isAdmin= User.isAdmin(UserUtils.getUser().getId());
		if(!isAdmin){
			List<Role> lists=UserUtils.getRoleList();
			for(Role role:lists){
				if(role.getEnname().equals("farmerBoss")){
					farmland.setUser(UserUtils.getUser());
				}
				if(role.getEnname().equals("farmerWork")){
					machineRecord.setUsedId(UserUtils.getUser().getId());
				}
			}
		}
		List<Farmland> farmlands=farmlandService.findList(farmland);
		model.addAttribute("lists",farmlands);
		model.addAttribute("machineRecord", machineRecord);
		return "manager/machine/machineRecordForm";
	}

	@RequiresPermissions("machine:machineRecord:edit")
	@RequestMapping(value = "save")
	public String save(MachineRecord machineRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, machineRecord)){
			return form(machineRecord, model);
		}
			if(machineRecord.getFarmlandId()!=null&&machineRecord.getFarmlandId()!=""){
				Farmland farmland=farmlandService.get(machineRecord.getFarmlandId());
				if(farmland!=null&&farmland.getId()!=null&&farmland.getId()!="") {
					machineRecord.setUser(farmland.getUser());
					machineRecord.setUsedId(farmland.getUsedId());
					machineRecord.setFarmerId(farmland.getFarmer().getId());
				}
			}
		machineRecordService.save(machineRecord);
		addMessage(redirectAttributes, "保存加工记录成功");
		return "redirect:"+Global.getAdminPath()+"/machine/machineRecord/?repage";
	}
	
	@RequiresPermissions("machine:machineRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(MachineRecord machineRecord, RedirectAttributes redirectAttributes) {
		machineRecordService.delete(machineRecord);
		addMessage(redirectAttributes, "删除加工记录成功");
		return "redirect:"+Global.getAdminPath()+"/machine/machineRecord/?repage";
	}

}