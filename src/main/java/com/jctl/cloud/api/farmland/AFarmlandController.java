package com.jctl.cloud.api.farmland;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.OfficeService;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */
@Controller
@RequestMapping("farmland")
public class AFarmlandController {
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private FarmerService farmerService;

    @RequestMapping("list")
    @ResponseBody
    public Map listFarmland(Farmland farmland) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            List<Farmland> farmlandList = farmlandService.findListAll(farmland);
            if (farmlandList == null || farmlandList.size() == 0) {
                result.put("flag", "0");
                result.put("msg", "抱歉，没有到农田信息!");
                return result;
            }
            String[] propertys = new String[]{"id", "alias", "area", "plantVaritety", "nodeNumber"};
            List<Map<String, Object>> farmlands = new ArrayList();
            for (Farmland land : farmlandList) {
                Map<String, Object> map = new HashMap();
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(land, property));
                }
                farmlands.add(map);
            }
            result.put("flag", "1");
            result.put("info", farmlands);
            result.put("farmerId", farmland.getFarmerId());
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Map<String, String> saveOrUpdate(Farmland farmland) {
        Map<String, String> result = Maps.newHashMap();
        try {
            farmlandService.save(farmland);
            result.put("flag", "1");
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, String> deleteFarmland(Farmland farmland) {
        Map<String, String> result = Maps.newHashMap();
        try {
            farmlandService.delete(farmland);
            result.put("flag", "1");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "删除失败");
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public Map<String, Object> getFarmland(String id, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> result = Maps.newHashMap();
        try {
            Farmland farmland = farmlandService.get(id);
            String[] propert = new String[]{"id", "alias", "plantVaritety", "nodeNumber", "usedId", "usedName", "assigneTime", "area", "addr", "nodeNumber", "farmlandNum", "landType", "remarks", "", ""};

            if (farmland.getUser() != null && farmland.getUser().getId() != null && farmland.getUser().getId() != "") {
                propert[13] = "user";
            }
            if (farmland.getRelay() != null && farmland.getRelay().getId() != null && farmland.getRelay().getId() != "") {
                propert[14] = "relay";
            }
            List messages = new ArrayList();
            Map message = Maps.newHashMap();
            for (String pro : propert) {
                if (pro.equals("assigneTime")) {
                    if (farmland.getAssigneTime() == null || farmland.getAssigneTime().equals("")) {
                        message.put("assigneTime", null);
                    } else {
                        message.put("assigneTime", sdf.format(farmland.getAssigneTime()));
                    }
                } else if (pro.equals("area")) {
                    if (farmland.getArea() == null) {
                        message.put("area", "0.00");
                    } else {
                        message.put("area", farmland.getArea());
                    }
                } else {
                    message.put(pro, Reflections.invokeGetter(farmland, pro));
                }
            }
            Farmer farmer = new Farmer();
            farmer.setUser(FrontUserUtils.getUser());
            List<Farmer> farmers = farmerService.findList(farmer);
            List data = new ArrayList();
            String[] property1 = new String[]{"id", "name"};
            for (Farmer farmer1 : farmers) {
                Map dataMap = Maps.newHashMap();
                for (String pp : property1) {
                    dataMap.put(pp, Reflections.invokeGetter(farmer1, pp));
                }
                data.add(dataMap);
            }
            List userList = new ArrayList();
            List<User> listUser = systemService.findUserByComplayIdAndUserId(FrontUserUtils.getUser());
            String[] property2 = new String[]{"id", "name"};
            if (listUser != null && listUser.size() > 0) {
                for (User of : listUser) {
                    Map offMaps = Maps.newHashMap();
                    for (String pr2 : property2) {
                        offMaps.put(pr2, Reflections.invokeGetter(of, pr2));
                    }
                    userList.add(offMaps);
                }
            }
            messages.add(message);
            result.put("flag", "1");
            result.put("farmerList", data);
            result.put("userList", userList);
            result.put("info", messages);
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }

    @RequestMapping("getUser")
    @ResponseBody
    public Map getUser(String userId) {
        Map result = Maps.newHashMap();
        try {
            List userList = new ArrayList();
            List<User> listUser = systemService.findUserByComplayIdAndUserId(UserUtils.get(userId));
            if (listUser.size() > 0) {
                String[] property2 = new String[]{"id", "name"};
                for (User of : listUser) {
                    Map offMaps = Maps.newHashMap();
                    for (String pr2 : property2) {
                        offMaps.put(pr2, Reflections.invokeGetter(of, pr2));
                    }
                    userList.add(offMaps);
                }
                result.put("flag", "1");
                result.put("info", userList);
            } else {
                result.put("flag", "0");
                result.put("msg", "公司下无任何人员!");
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }
}
