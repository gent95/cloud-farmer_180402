/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodehistory.web;

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
import com.jctl.cloud.manager.nodehistory.entity.NodeHistory;
import com.jctl.cloud.manager.nodehistory.service.NodeHistoryService;

/**
 * 历史命令Controller
 * @author kay
 * @version 2017-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/nodehistory/nodeHistory")
public class NodeHistoryController extends BaseController {

	@Autowired
	private NodeHistoryService nodeHistoryService;
	
	@ModelAttribute
	public NodeHistory get(@RequestParam(required=false) String id) {
		NodeHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = nodeHistoryService.get(id);
		}
		if (entity == null){
			entity = new NodeHistory();
		}
		return entity;
	}
	
	@RequiresPermissions("nodehistory:nodeHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(NodeHistory nodeHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			Page<NodeHistory> page = nodeHistoryService.findPage(new Page<NodeHistory>(request, response), nodeHistory);
			model.addAttribute("page", page);
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return "manager/nodehistory/nodeHistoryList";
	}

	@RequiresPermissions("nodehistory:nodeHistory:view")
	@RequestMapping(value = "form")
	public String form(NodeHistory nodeHistory, Model model) {
		try{
			model.addAttribute("nodeHistory", nodeHistory);
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return "manager/nodehistory/nodeHistoryForm";
	}

	@RequiresPermissions("nodehistory:nodeHistory:edit")
	@RequestMapping(value = "save")
	public String save(NodeHistory nodeHistory, Model model, RedirectAttributes redirectAttributes) {
        try{
            if (!beanValidator(model, nodeHistory)){
                    return form(nodeHistory, model);
                }
                nodeHistoryService.save(nodeHistory);
                addMessage(redirectAttributes, "保存历史命令成功");
            }catch(Exception e){
                addMessage(redirectAttributes, "保存历史命令失败");
                e.printStackTrace();
            }
		return "redirect:"+Global.getAdminPath()+"/nodehistory/nodeHistory/?repage";
	}
	
	@RequiresPermissions("nodehistory:nodeHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(NodeHistory nodeHistory, RedirectAttributes redirectAttributes) {
	    try{
            nodeHistoryService.delete(nodeHistory);
            addMessage(redirectAttributes, "删除历史命令成功");
		}catch(Exception e){
		    addMessage(redirectAttributes, "删除历史命令失败");
		 	e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/nodehistory/nodeHistory/?repage";
	}

}