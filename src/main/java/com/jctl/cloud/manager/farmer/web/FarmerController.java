/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmer.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.manager.plant.entity.PlantVariety;
import com.jctl.cloud.manager.plant.service.PlantVarietyService;
import com.jctl.common.entity.RoleEnum;
import com.jctl.common.utils.UserUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;

/**
 * 农场Controller
 *
 * @author lewkay
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/farmer/farmer")
public class FarmerController extends BaseController {

    @Autowired
    private FarmerService farmerService;
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private RelayService relayService;
    @Autowired
    private  NodeService nodeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private PlantVarietyService plantVarietyService;

    @Value("#{config['farmerBoss']}")
    private String farmerBoss;

    @Value("#{config['farmerWork']}")
    private String farmerWork;

    @ModelAttribute
    public Farmer get(@RequestParam(required = false) String id) {
        Farmer entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = farmerService.get(id);
        }
        if (entity == null) {
            entity = new Farmer();
        }
        return entity;
    }

    @RequiresPermissions("farmer:farmer:view")
    @RequestMapping(value = {"list", ""})
    public String list(Farmer farmer, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<Farmer> page = null;
//            User user = UserUtils.getUser();
//            boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
//            if (!isAdmin) {
//                List<Role> list = UserUtils.getRoleList();
//                for (Role role : list) {
//                    if (role.getEnname().equals(farmerBoss)) {
//                        farmer.setUser(user);
//                    }
//                }
//            }
            if(UserUtil.getUserRoleType() == RoleEnum.farmerBoss){
                farmer.setUser(UserUtils.getUser());
            }
            //应急预案
            page = farmerService.findPage(new Page<Farmer>(request, response), farmer);
            model.addAttribute("page", page);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "manager/farmer/farmerList";
    }

    @RequiresPermissions("farmer:farmer:view")
    @RequestMapping(value = "form")
    public String form(Farmer farmer, Model model) {

        if(farmer.getId()!=null&&farmer.getId()!="") {
            Relay relay = new Relay();
            Farmland farmland=new Farmland();
//            boolean isAdmin=User.isAdmin(UserUtils.getUser().getId());
//            if(!isAdmin){
//                List<Role> list=UserUtils.getRoleList();
//                for(Role role:list){
//                    if(role.getEnname().equals(farmerBoss)){
//                        relay.setUser(UserUtils.getUser());
//                        farmland.setUser(UserUtils.getUser());
//                    }
//                }
//            }
            if(UserUtil.getUserRoleType() == RoleEnum.farmerBoss){
                relay.setUser(UserUtils.getUser());
                farmland.setUser(UserUtils.getUser());
            }
            farmland.setFarmer(farmer);
            relay.setFarmer(farmer);
            Page<Farmland> faPage=farmlandService.findPage(new Page<Farmland>(),farmland);
            Page<Relay> page = relayService.findPage(new Page<Relay>(), relay);
            model.addAttribute("page", page);
            model.addAttribute("faPage",faPage);
        }
        PlantVariety plantVariety=new PlantVariety();
        plantVariety.setCreateBy(UserUtils.getUser());
        List<PlantVariety> plantVarieties=plantVarietyService.findList(plantVariety);
        model.addAttribute("plants",plantVarieties);
        model.addAttribute("farmer", farmer);
        return "manager/farmer/farmerForm";
    }

    @RequiresPermissions("farmer:farmer:edit")
    @RequestMapping(value = "save")
    public String save(Farmer farmer, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, farmer)) {
            return form(farmer, model);
        }
        try {
            if (farmer.getId() == null || farmer.getId().equals("")) {
                farmer.setUser(UserUtils.getUser());
            }else {
                Relay relay = new Relay();
                relay.setFarmer(farmer);
                List<Relay> lists = relayService.findList(relay);
                if (lists.size() > 0) {
                    for (Relay re : lists) {
                        User user = systemService.getUser(farmer.getUsedId());
                        re.setUsed(user);
                        relayService.save(re);
                    }
                }
            }
            farmerService.save(farmer);
            addMessage(redirectAttributes, "保存农场成功");
        }catch (Exception e){
            addMessage(redirectAttributes, "保存农场錯誤");
            e.printStackTrace();
        }
        return "redirect:" + Global.getAdminPath() + "/farmer/farmer/?repage";
    }

    @RequiresPermissions("farmer:farmer:edit")
    @RequestMapping(value = "delete")
    public String delete(Farmer farmer, RedirectAttributes redirectAttributes) {
        try {
            Farmland farmland=new Farmland();
            farmland.setFarmer(farmerService.get(farmer.getId()));
            farmland.setDelFlag("0");
            List<Farmland> lists=farmlandService.findList(farmland);
            if(lists!=null&&lists.size()>0){
                for(Farmland fa:lists){
                    fa.setDelFlag("0");
                    Node no=new Node();
                    no.setFarmlandId(fa.getId());
                    List<Node> nodeList=nodeService.findList(no);
                    if(nodeList!=null&&nodeList.size()>0) {
                        for (Node node : nodeList) {
                            node.setFarmlandId(null);
                            node.setUser(null);
                            node.setUsedId(null);
                            nodeService.save(node);
                        }
                    }
                    farmlandService.delete(fa);
                }
            }
            farmer.setDelFlag("0");
            Relay relay=new Relay();
            relay.setFarmer(farmer);
            relay.setDelFlag("0");
            List<Relay> relayList=relayService.findList(relay);
            if(relayList!=null||relayList.size()>0){
                for(Relay relay1:relayList){
                    relay1.setFarmer(null);
                    relay1.setUsed(null);
                    relay1.setUsed(null);
                    relayService.save(relay1);
                }
            }
            farmerService.delete(farmer);
            addMessage(redirectAttributes, "删除农场成功");
        }catch (Exception E){
            addMessage(redirectAttributes, "删除农场失败");
            E.printStackTrace();
        }
        return "redirect:" + Global.getAdminPath() + "/farmer/farmer/?repage";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeDate")
    public List<Map<Object, Object>> treeDate(HttpServletResponse response, Farmer farmer) {
        List<Map<Object, Object>> mapList = new ArrayList<Map<Object, Object>>();

        User user = UserUtils.getUser();
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        if (!isAdmin && UserUtil.getUserRoleType()==RoleEnum.farmerBoss) {
                    farmer.setUser(user);
        }
        //查询未删除的
        farmer.setDelFlag("1");
        List<Farmer> list = farmerService.findList(farmer);

        for (int i = 0; i < list.size(); i++) {
            Farmer fa = list.get(i);
            Map<Object, Object> map = Maps.newHashMap();
            map.put("id", fa.getId());
            map.put("name", StringUtils.replace(fa.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }


}