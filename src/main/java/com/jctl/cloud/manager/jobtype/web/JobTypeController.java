/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.jobtype.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jctl.cloud.manager.jobtype.entity.JobType;
import com.jctl.cloud.manager.jobtype.service.JobTypeService;

/**
 * 作业类型Controller
 * @author tulanlan
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/jobtype/jobType")
public class JobTypeController extends BaseController {

	@Autowired
	private JobTypeService jobTypeService;
	
	@ModelAttribute
	public JobType get(@RequestParam(required=false) String id) {
		JobType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobTypeService.get(id);
		}
		if (entity == null){
			entity = new JobType();
		}
		return entity;
	}
	
//	@RequiresPermissions("jobtype:jobType:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobType jobType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobType> page = jobTypeService.findPage(new Page<JobType>(request, response), jobType); 
		model.addAttribute("page", page);
		return "manager/jobtype/jobTypeList";
	}

//	@RequiresPermissions("jobtype:jobType:view")
	@RequestMapping(value = "form")
	public String form(JobType jobType, Model model) {
		model.addAttribute("jobType", jobType);
		return "manager/jobtype/jobTypeForm";
	}

//	@RequiresPermissions("jobtype:jobType:edit")
	@RequestMapping(value = "save")
	public String save(JobType jobType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobType)){
			return form(jobType, model);
		}
		jobTypeService.save(jobType);
		addMessage(redirectAttributes, "保存作业类型成功");
		return "redirect:"+Global.getAdminPath()+"/jobtype/jobType/?repage";
	}
	
//	@RequiresPermissions("jobtype:jobType:edit")
	@RequestMapping(value = "delete")
	public String delete(JobType jobType, RedirectAttributes redirectAttributes) {
		jobTypeService.delete(jobType);
		addMessage(redirectAttributes, "删除作业类型成功");
		return "redirect:"+Global.getAdminPath()+"/jobtype/jobType/?repage";
	}

}