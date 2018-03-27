package com.jctl.cloud.api.message;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.message.service.WaringMessageService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.FrontUserUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gent on 2017/3/24.
 */
@Controller
@RequestMapping("waringmsg")
public class AmessageController {

    @Autowired
    private WaringMessageService waringMessageService;
    @Autowired
    private NodeService nodeService;


    @RequestMapping("list")
    @ResponseBody
    public Map List() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map result = Maps.newHashMap();
        try {
            List<WaringMessage> page = waringMessageService.findListByUser(FrontUserUtils.getUser());
            if(page.size()==0){
                result.put("flag", 0);
                result.put("msg", "没有预警信息！");
                return result;
            }
            List info = new ArrayList<>();
            for (WaringMessage w : page) {
                Node nodesss = nodeService.getByNodeNum(w.getNodeNum());
                String[] property = new String[]{"id","nodeId","status","nodeNum", "nodeName", "message", "property", "createDate"};
                Map maps = Maps.newHashMap();
                for (String p : property) {
                    if (p == "nodeName") {
                        maps.put(p, nodesss.getNodeAlise());
                    } else if (p == "createDate") {
                        maps.put(p, sdf.format(w.getCreateDate()));
                    } else {
                        maps.put(p, Reflections.invokeGetter(w, p));
                    }
                }
                info.add(maps);
            }
            result.put("flag", 1);
            result.put("info", info);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
        }
        return result;
    }
}
