/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.msgsend.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.jctl.cloud.manager.msgsend.iputil.IpUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.manager.msgsend.entity.MsgSend;
import com.jctl.cloud.manager.msgsend.service.MsgSendService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 短信发送记录Controller
 * @author ll
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/msgsend/msgSend")
public class MsgSendController extends BaseController {

	@Autowired
	private MsgSendService msgSendService;
	
	@ModelAttribute
	public MsgSend get(@RequestParam(required=false) String id) {
		MsgSend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = msgSendService.get(id);
		}
		if (entity == null){
			entity = new MsgSend();
		}
		return entity;
	}
	
	@RequiresPermissions("msgsend:msgSend:view")
	@RequestMapping(value = {"list", ""})
	public String list(MsgSend msgSend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MsgSend> page = msgSendService.findPage(new Page<MsgSend>(request, response), msgSend); 
		model.addAttribute("page", page);
		return "manager/msgsend/msgSendList";
	}

	@RequiresPermissions("msgsend:msgSend:view")
	@RequestMapping(value = "form")
	public String form(MsgSend msgSend, Model model) {
		model.addAttribute("msgSend", msgSend);
		return "manager/msgsend/msgSendForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map save(String  iphone, Model model, RedirectAttributes redirectAttributes) {
		Map result= Maps.newHashMap();
		MsgSend msgSend=new MsgSend();
		try {
			msgSend.setIphone(iphone);
			msgSend.setIp(IpUtil.getIp());
			msgSend.setAddTime(new Date());
			msgSendService.save(msgSend);
			result.put("flag", "1");
			result.put("msg", "添加成功");
		List<MsgSend> lists=msgSendService.findListNumber(msgSend);
		result.put("number",lists.size());
		}catch (Exception e){
			result.put("flag","0");
			result.put("msg","添加失败");
		}
	/*	return "redirect:"+Global.getAdminPath()+"/msgsend/msgSend/?repage";*/
	return  result;
	}
	
	@RequiresPermissions("msgsend:msgSend:edit")
	@RequestMapping(value = "delete")
	public String delete(MsgSend msgSend, RedirectAttributes redirectAttributes) {
		msgSendService.delete(msgSend);
		addMessage(redirectAttributes, "删除短信记录成功");
		return "redirect:"+Global.getAdminPath()+"/msgsend/msgSend/?repage";
	}

}