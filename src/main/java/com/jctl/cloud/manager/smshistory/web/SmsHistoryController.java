/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.smshistory.web;

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
import com.jctl.cloud.manager.smshistory.entity.SmsHistory;
import com.jctl.cloud.manager.smshistory.service.SmsHistoryService;

/**
 * 短信记录Controller
 * @author kay
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/smshistory/smsHistory")
public class SmsHistoryController extends BaseController {

	@Autowired
	private SmsHistoryService smsHistoryService;
	
	@ModelAttribute
	public SmsHistory get(@RequestParam(required=false) String id) {
		SmsHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsHistoryService.get(id);
		}
		if (entity == null){
			entity = new SmsHistory();
		}
		return entity;
	}
	
	@RequiresPermissions("smshistory:smsHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsHistory smsHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsHistory> page = smsHistoryService.findPage(new Page<SmsHistory>(request, response), smsHistory); 
		model.addAttribute("page", page);
		return "manager/smshistory/smsHistoryList";
	}

	@RequiresPermissions("smshistory:smsHistory:view")
	@RequestMapping(value = "form")
	public String form(SmsHistory smsHistory, Model model) {
		model.addAttribute("smsHistory", smsHistory);
		return "manager/smshistory/smsHistoryForm";
	}

	@RequiresPermissions("smshistory:smsHistory:edit")
	@RequestMapping(value = "save")
	public String save(SmsHistory smsHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsHistory)){
			return form(smsHistory, model);
		}
		smsHistoryService.save(smsHistory);
		addMessage(redirectAttributes, "保存短信记录成功");
		return "redirect:"+Global.getAdminPath()+"/smshistory/smsHistory/?repage";
	}
	
	@RequiresPermissions("smshistory:smsHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsHistory smsHistory, RedirectAttributes redirectAttributes) {
		smsHistoryService.delete(smsHistory);
		addMessage(redirectAttributes, "删除短信记录成功");
		return "redirect:"+Global.getAdminPath()+"/smshistory/smsHistory/?repage";
	}

}