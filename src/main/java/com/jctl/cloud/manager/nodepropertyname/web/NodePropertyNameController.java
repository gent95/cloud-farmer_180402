/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodepropertyname.web;

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
import com.jctl.cloud.manager.nodepropertyname.entity.NodePropertyName;
import com.jctl.cloud.manager.nodepropertyname.service.NodePropertyNameService;

/**
 * 别名Controller
 *
 * @author kay
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/nodepropertyname/nodePropertyName")
public class NodePropertyNameController extends BaseController {

    @Autowired
    private NodePropertyNameService nodePropertyNameService;

    @ModelAttribute
    public NodePropertyName get(@RequestParam(required = false) String id) {
        NodePropertyName entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = nodePropertyNameService.get(id);
        }
        if (entity == null) {
            entity = new NodePropertyName();
        }
        return entity;
    }

    @RequiresPermissions("nodepropertyname:nodePropertyName:view")
    @RequestMapping(value = {"list", ""})
    public String list(NodePropertyName nodePropertyName, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<NodePropertyName> page = nodePropertyNameService.findPage(new Page<NodePropertyName>(request, response), nodePropertyName);
        model.addAttribute("page", page);
        model.addAttribute("nodeId", nodePropertyName.getNodeId());
        return "manager/nodepropertyname/nodePropertyNameList";
    }

    @RequiresPermissions("nodepropertyname:nodePropertyName:view")
    @RequestMapping(value = "form")
    public String form(NodePropertyName nodePropertyName, Model model) {
        model.addAttribute("nodePropertyName", nodePropertyName);
        model.addAttribute("nodeId", nodePropertyName.getNodeId());
        return "manager/nodepropertyname/nodePropertyNameForm";
    }

    @RequiresPermissions("nodepropertyname:nodePropertyName:edit")
    @RequestMapping(value = "save")
    public String save(NodePropertyName nodePropertyName, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, nodePropertyName)) {
            return form(nodePropertyName, model);
        }
        try {
            if(nodePropertyName.getId() != null && !nodePropertyName.getId().equals("")){
                NodePropertyName search=  nodePropertyNameService.getByUserAndType(nodePropertyName);
                if(search!=null){
                    addMessage(redirectAttributes, "此采集点已经有命名，无需重复添加！");
                }else {
                    nodePropertyNameService.save(nodePropertyName);
                }
            }else {
                NodePropertyName search=  nodePropertyNameService.getByUserAndType(nodePropertyName);
                if(search!= null){
                    addMessage(redirectAttributes, "此采集点已经有命名，无需重复添加！");
                }else {
                    nodePropertyNameService.save(nodePropertyName);
                }
            }
         } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "保存别名失败");
        }
        return "redirect:" + Global.getAdminPath() + "/nodepropertyname/nodePropertyName/?repage&nodeId="+nodePropertyName.getNodeId();
    }

    @RequiresPermissions("nodepropertyname:nodePropertyName:edit")
    @RequestMapping(value = "delete")
    public String delete(NodePropertyName nodePropertyName, RedirectAttributes redirectAttributes) {
        nodePropertyNameService.delete(nodePropertyName);
        addMessage(redirectAttributes, "删除别名成功");
        return "redirect:" + Global.getAdminPath() + "/nodepropertyname/nodePropertyName/?repage&nodeId="+nodePropertyName.getNodeId();
    }

}