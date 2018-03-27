/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.app.banner.web;

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
import com.jctl.cloud.app.banner.entity.AppBanner;
import com.jctl.cloud.app.banner.service.AppBannerService;

/**
 * APP轮播Controller
 *
 * @author 刘凯
 * @version 2017-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/banner/appBanner")
public class AppBannerController extends BaseController {

    @Autowired
    private AppBannerService appBannerService;

    @ModelAttribute
    public AppBanner get(@RequestParam(required = false) String id) {
        AppBanner entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = appBannerService.get(id);
        }
        if (entity == null) {
            entity = new AppBanner();
        }
        return entity;
    }

    @RequiresPermissions("banner:appBanner:view")
    @RequestMapping(value = {"list", ""})
    public String list(AppBanner appBanner, HttpServletRequest request, HttpServletResponse response, Model model) {

        try {
            Page<AppBanner> page = appBannerService.findPage(new Page<AppBanner>(request, response), appBanner);
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "app/banner/appBannerList";
    }

    @RequiresPermissions("banner:appBanner:view")
    @RequestMapping(value = "form")
    public String form(AppBanner appBanner, Model model) {
        try {
            model.addAttribute("appBanner", appBanner);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "app/banner/appBannerForm";
    }

    @RequiresPermissions("banner:appBanner:edit")
    @RequestMapping(value = "save")
    public String save(AppBanner appBanner, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, appBanner)) {
            return form(appBanner, model);
        }
        try{
            appBannerService.save(appBanner);
            addMessage(redirectAttributes, "保存APP轮播成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:" + Global.getAdminPath() + "/banner/appBanner/?repage";
    }

    @RequiresPermissions("banner:appBanner:edit")
    @RequestMapping(value = "delete")
    public String delete(AppBanner appBanner, RedirectAttributes redirectAttributes) {
        try{
            appBannerService.delete(appBanner);
            addMessage(redirectAttributes, "删除APP轮播成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:" + Global.getAdminPath() + "/banner/appBanner/?repage";
    }

}