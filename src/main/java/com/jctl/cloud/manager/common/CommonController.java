package com.jctl.cloud.manager.common;

import com.jctl.cloud.app.version.entity.AppVersion;
import com.jctl.cloud.app.version.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/9.
 */
@Controller
@RequestMapping(value = "${adminPath}/common")
public class CommonController {

    @Autowired
    private AppVersionService appVersionService;
    @RequestMapping(value = "appDown")
    public String appDown(HttpServletRequest request, Model model){


        AppVersion appVersion=  appVersionService.findNewVersion();
        model.addAttribute("version",appVersion);

        return "common/appDown";
    }


}
