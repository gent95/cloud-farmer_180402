/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.version.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.common.utils.http.FtpUploadUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.app.version.entity.AppVersion;
import com.jctl.cloud.app.version.service.AppVersionService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 安卓版本Controller
 * @author kay
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/version/appVersion")
public class AppVersionController extends BaseController {

	@Autowired
	private AppVersionService appVersionService;
	
	@ModelAttribute
	public AppVersion get(@RequestParam(required=false) String id) {
		AppVersion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appVersionService.get(id);
		}
		if (entity == null){
			entity = new AppVersion();
		}
		return entity;
	}
	
	@RequiresPermissions("version:appVersion:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			Page<AppVersion> page = appVersionService.findPage(new Page<AppVersion>(request, response), appVersion);
			model.addAttribute("page", page);
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return "app/version/appVersionList";
	}

	@RequiresPermissions("version:appVersion:view")
	@RequestMapping(value = "form")
	public String form(AppVersion appVersion, Model model) {
		try{
			model.addAttribute("appVersion", appVersion);
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return "app/version/appVersionForm";
	}

	@RequiresPermissions("version:appVersion:edit")
	@RequestMapping(value = "save")
	public String save(AppVersion appVersion, Model model, RedirectAttributes redirectAttributes) {
        try{
            if (!beanValidator(model, appVersion)){
                    return form(appVersion, model);
                }
                appVersionService.save(appVersion);
                addMessage(redirectAttributes, "保存安卓版本成功");
            }catch(Exception e){
                addMessage(redirectAttributes, "保存安卓版本失败");
                e.printStackTrace();
            }
		return "redirect:"+Global.getAdminPath()+"/version/appVersion/?repage";
	}
	
	@RequiresPermissions("version:appVersion:edit")
	@RequestMapping(value = "delete")
	public String delete(AppVersion appVersion, RedirectAttributes redirectAttributes) {
	    try{
            appVersionService.delete(appVersion);
            addMessage(redirectAttributes, "删除安卓版本成功");
		}catch(Exception e){
		    addMessage(redirectAttributes, "删除安卓版本失败");
		 	e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/version/appVersion/?repage";
	}

	@RequestMapping(value="uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam(value = "file", required = false)MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{

		Map<String, Object> resultMap = new HashMap<>();
		try {
			/*MultipartHttpServletRequest multipartRequest =
					(MultipartHttpServletRequest) request;
			Iterator<String> fileNames = multipartRequest.getFileNames();
			MultipartFile file = multipartRequest.getFile(fileNames.next());*/
			Map ups = FtpUploadUtil.upload(file.getInputStream(), file);
			if (ups.get("flag") != null && ups.get("flag").equals("1")) {
				resultMap.put("flag", 1);//成功
				resultMap.put("url", ups.get("url"));//url

			}
		} catch (Exception e) {
			resultMap.put("flag", 0);
			resultMap.put("code", 0);
			resultMap.put("msg", "操作失败");
			e.printStackTrace();
		}
		return resultMap;
	}
}