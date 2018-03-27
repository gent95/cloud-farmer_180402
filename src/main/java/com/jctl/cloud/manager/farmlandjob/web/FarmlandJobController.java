/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmlandjob.web;

import java.util.List;

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
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.farmlandjob.entity.FarmlandJob;
import com.jctl.cloud.manager.farmlandjob.service.FarmlandJobService;
import com.jctl.cloud.manager.jobtype.entity.JobType;
import com.jctl.cloud.manager.jobtype.service.JobTypeService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;

/**
 * 作业管理Controller
 * @author ll
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/farmlandjob/farmlandJob")
public class FarmlandJobController extends BaseController {

	@Autowired
	private FarmlandJobService farmlandJobService;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private FarmlandService farmlandService;
	@ModelAttribute
	public FarmlandJob get(@RequestParam(required=false) String id) {
		FarmlandJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = farmlandJobService.get(id);
		}
		if (entity == null){
			entity = new FarmlandJob();
		}
		return entity;
	}
	
	@RequiresPermissions("farmlandjob:farmlandJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(FarmlandJob farmlandJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean isAdmin= User.isAdmin(UserUtils.getUser().getId());
		if(!isAdmin){
			List<Role> roles=UserUtils.getRoleList();
			for(Role role:roles){
				if(role.getEnname().equals("farmerBoss")){
					farmlandJob.setUser(UserUtils.getUser());
				}
				if(role.getEnname().equals("farmerWork")){
					farmlandJob.setUsedId(UserUtils.getUser().getId());
				}
			}
		}
		Page<FarmlandJob> page = farmlandJobService.findPage(new Page<FarmlandJob>(request, response), farmlandJob); 
		model.addAttribute("page", page);
		return "manager/farmlandjob/farmlandJobList";
	}

	@RequiresPermissions("farmlandjob:farmlandJob:view")
	@RequestMapping(value = "form")
	public String form(FarmlandJob farmlandJob, Model model) {
		List<JobType> lists=jobTypeService.findList(new JobType());
		Farmland farmland=new Farmland();
		boolean isAdmin= User.isAdmin(UserUtils.getUser().getId());
		if(!isAdmin){
			List<Role> roles=UserUtils.getRoleList();
			for(Role role:roles){
				if(role.getEnname().equals("farmerBoss")){
					farmland.setUser(UserUtils.getUser());
				}
				if(role.getEnname().equals("farmerWork")){
				farmland.setUsedId(UserUtils.getUser().getId());
				}
			}
		}
		List<Farmland> farmlands=farmlandService.findList(farmland);
		model.addAttribute("listFarm",farmlands);
		model.addAttribute("farmlandJob", farmlandJob);
		model.addAttribute("lists",lists);
		return "manager/farmlandjob/farmlandJobForm";
	}

	@RequiresPermissions("farmlandjob:farmlandJob:edit")
	@RequestMapping(value = "save")
	public String save(FarmlandJob farmlandJob, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, farmlandJob)){
			return form(farmlandJob, model);
		}
		if("".equals(farmlandJob.getId())||farmlandJob.getId()==null){
			farmlandJob.setFlag("3");
		}
		if(farmlandJob.getFarmerlandId()!=null&&farmlandJob.getFarmerlandId()!=""){
			Farmland farmland=farmlandService.get(farmlandJob.getFarmerlandId());
			if(farmland!=null&&farmland.getId()!=null&&farmland.getId()!="") {
				farmlandJob.setUser(farmland.getUser());
				farmlandJob.setUsedId(farmland.getUsedId());
			}
		}
		farmlandJobService.save(farmlandJob);
		addMessage(redirectAttributes, "保存作业管理成功");
		return "redirect:"+Global.getAdminPath()+"/farmlandjob/farmlandJob/?repage";
	}
	
	@RequiresPermissions("farmlandjob:farmlandJob:edit")
	@RequestMapping(value = "delete")
	public String delete(FarmlandJob farmlandJob, RedirectAttributes redirectAttributes) {
		farmlandJobService.delete(farmlandJob);
		addMessage(redirectAttributes, "删除作业管理成功");
		return "redirect:"+Global.getAdminPath()+"/farmlandjob/farmlandJob/?repage";
	}

}