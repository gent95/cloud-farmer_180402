package com.jctl.cloud.api.farmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/23.
 */
@Controller
@RequestMapping("farmer")
public class AFarmerController {
    @Autowired
    private FarmerService farmerService;

    @Autowired
    private SystemService systemService;

    /**
     * 农场列表
     *
     * @param farmer
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(Farmer farmer) {
        Map<String, Object> result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            farmer.preSearch();
            List<Farmer> lists = farmerService.findListAll(farmer);
            if (lists != null && lists.size() > 0) {
                String[] propertys = new String[]{"id", "name", "addr", "farmlandNumber", "plantVariety", "lng", "lat"};
                for (Farmer fa : lists) {
                    Map map = new HashMap();
                    for (String property : propertys) {
                        map.put(property, Reflections.invokeGetter(fa, property));
                    }
                    list.add(map);
                }
                result.put("flag", 1);
                result.put("info", list);
            }else {
                result.put("flag",0);
                result.put("msg", "暂无数据！");
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("allFarmers")
    @ResponseBody
    public Map<String, Object> allFarmers(Farmer farmer) {
        Map<String, Object> result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            farmer.preSearch();
            List<Farmer> lists = farmerService.findListAllFarmers(farmer);
            if (lists != null && lists.size() > 0) {
                String[] propertys = new String[]{"id", "name"};
                for (Farmer fa : lists) {
                    Map<String,Object> map = new HashMap();
                    for (String property : propertys) {
                        map.put(property, Reflections.invokeGetter(fa, property));
                    }
                    list.add(map);
                }
                result.put("flag", 1);
                result.put("info", list);
            }else {
                result.put("flag",0);
                result.put("msg", "暂无数据！");
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Map saveOrUpdate(Farmer farmer) {
        Map result = Maps.newHashMap();
        try {
            farmer.setUser(FrontUserUtils.getUser());
            farmerService.save(farmer);
            result.put("msg", "操作成功");
            result.put("flag", "1");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "添加失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map deleteFarmer(Farmer farmer) {
        Map result = Maps.newHashMap();
        try {
            farmerService.delete(farmer);
            result.put("flag", "1");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "删除失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public Map getFarmer(String id) {
        Map result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            Farmer farmer = farmerService.get(id);
            String[] propert = new String[]{"id", "name", "farmerNum", "addr", "area", "plantVariety", "user.name",
                    "user.id", "farmlandNumber", "relayNumber", "remarks"};
            Map maps = Maps.newHashMap();
            for (String pro : propert) {
                maps.put(pro, Reflections.invokeGetter(farmer, pro));
            }
            list.add(maps);
            result.put("info", list);
            result.put("flag", "1");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }
}
