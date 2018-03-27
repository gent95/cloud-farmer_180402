package com.jctl.cloud.api.version;

import com.jctl.cloud.app.version.entity.AppVersion;
import com.jctl.cloud.app.version.service.AppVersionService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/23.
 */
@Controller
@RequestMapping("version")
public class AVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @RequestMapping("check")
    @ResponseBody
    public Map<String, Object> check(AppVersion version) {
        Map<String, Object> result = new HashedMap();
        AppVersion last = appVersionService.findNewVersion();
        if (last.getInVersion().equals(version.getInVersion())) {
            result.put("flag", 0);
            result.put("msg", "当前已经是最新版本！");
            return result;
        } else {
            result.put("flag", 1);
            result.put("version", last);
            return result;
        }
    }
}
