package com.jctl.cloud.api.realy;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.FrontUserUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewKay on 2017/4/5.
 */

@Controller
@RequestMapping("relay")
public class ARelayController {


    @Autowired
    private RelayService relayService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private FarmerService farmerService;


    @RequestMapping("list")
    @ResponseBody
    public Map list(HttpServletRequest request, Relay relay) {
        Map result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            User user = FrontUserUtils.getUser();
            if (user != null) {
                boolean AdminUser = User.isAdmin(user.getId());
                if (!AdminUser) {
                    List<Role> rolse = user.getRoleList();
                    for (Role ro : rolse) {
                        if (ro.getEnname().equals("farmerWorker")) {
                            relay.setUsed(user);
                        }
                        if (ro.getEnname().equals("farmerBoss")) {
                            relay.setUser(user);
                        }
                    }
                }
            }
            List<Relay> lists = relayService.findListByUser(relay);
            String[] propertys = new String[]{"id", "name", "relayNum", "farmerName", "nodeNum", "powerSupply"};
            if (lists != null && lists.size() > 0) {
                for (Relay relay1 : lists) {
                    System.out.print(relay1);
                    Map map = new HashMap();
                    for (String property : propertys) {
                        map.put(property, Reflections.invokeGetter(relay1, property));
                    }
                    list.add(map);
                }
                result.put("flag", 1);
                result.put("info", list);
            } else if(lists.size()==0) {
                result.put("flag", 0);
                result.put("msg", "没有查询到中继信息!");
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("info")
    @ResponseBody
    public Map<String,Object> info(Relay relay) {
        Map result = new HashMap();
        List infoList = new ArrayList();
        try {
            relay = relayService.getRelayAndNodeNum(relay);

            if (relay.getUser() != null) {
                Farmer farmer = new Farmer();
                farmer.setUser(relay.getUser());
                List<Farmer> farmers = farmerService.findList(farmer);
                String[] poper = new String[]{"id", "name"};
                for (Farmer fa : farmers) {
                    Map message = Maps.newHashMap();
                    for (String pr : poper) {
                        message.put(pr, Reflections.invokeGetter(fa, pr));
                    }
                    infoList.add(message);
                }
            }
            result.put("info", infoList);
            result.put("flag", 1);
            result.put("data", relay);

        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map save(Relay relay) {
        Map result = new HashedMap();
        try {
            Relay res = relayService.getByMac(relay.getRelayNum());
            if (res != null) {
                result.put("flag", 0);
                result.put("msg", "此中继已被注册!");
                return result;
            }
            relay.setUser(FrontUserUtils.getUser());
            relayService.save(relay);
            result.put("flag", 1);
            result.put("msg", "保存成功!");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作异常!");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("update")
    @ResponseBody
    public Map update(Relay relay) {
        Map result = new HashedMap();
        try {
            relayService.save(relay);
            result.put("flag", 1);
            result.put("msg", "保存成功!");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作异常!");
        }
        return result;
    }

    /**
     *
     * @param relay
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map delete(Relay relay) {
        Map result = new HashedMap();
        try {
            relayService.delete(relay);
            result.put("flag", 1);
            result.put("msg", "删除成功!");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作异常!");
        }
        return result;
    }

}
