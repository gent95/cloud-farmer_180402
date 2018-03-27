package com.jctl.cloud.manager.farmerland.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;
import com.jctl.cloud.manager.plant.entity.PlantVariety;
import com.jctl.cloud.manager.plant.service.PlantVarietyService;
import com.jctl.cloud.manager.relay.entity.Relay;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;

/**
 * 农田(大棚)Controller
 *
 * @author kay
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/farmerland/farmland")
public class FarmlandController extends BaseController {

    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private RelayService relayService;
    @Autowired
    private NodeDataDetailsService nodeDataDetailsService;
    @Autowired
    private PlantVarietyService plantVarietyService;


    @ModelAttribute
    public Farmland get(@RequestParam(required = false) String id) {
        Farmland entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = farmlandService.get(id);
        }
        if (entity == null) {
            entity = new Farmland();
        }
        return entity;
    }
    @RequestMapping("farmlandDetail")
    public String list(Farmer farmer, Model model){
        Farmland farmland=new Farmland();
        boolean isAdmin=User.isAdmin(UserUtils.getUser().getId());
        if(!isAdmin){
            List<Role> roles=UserUtils.getRoleList();
            for(Role role:roles){
                if(role.getEnname().equals(FARMER_BOSS)){
                    farmland.setUser(UserUtils.getUser());
                }
                if(role.getEnname().equals(FARMER_WORKER)){
                    farmland.setUsedId(UserUtils.getUser().getId());
                }
            }
        }
        Relay relay=new Relay();
        if(farmer!=null){
            if(farmer.getId()!=null&&!"".equals(farmer.getId())){
                farmland.setFarmer(farmer);

                relay.setFarmer(farmer);
                List<Farmland> farmlands=farmlandService.findList(farmland);
                List<Node> nodes=nodeService.getNodeListByFamer(farmer.getId());
                List<Relay> relays=relayService.findList(relay);
                List<Farmland> farmlands1=farmlandService.findPlant(farmland);
                model.addAttribute("relays",relays);
                model.addAttribute("nodes",nodes);
                model.addAttribute("farmlandList",farmlands);
                model.addAttribute("plantList",farmlands1);
            }
        }
        User user=UserUtils.getUser();
        model.addAttribute("user",user);
        model.addAttribute("farmerId",farmer.getId());
        return "manager/farmerland/farmlandListDetail";
    }
    @RequestMapping("/getData")
    @ResponseBody
    public Map reData(String farmerId) {
        Map result=Maps.newHashMap();
        if (farmerId != null && !"".equals(farmerId)) {
            List<NodeDataDetails> nodeDataDetailsList = nodeDataDetailsService.findAvgData(farmerId);

                result.put("data",nodeDataDetailsList);

        }
        return result;
    }

    @RequiresPermissions("farmerland:farmland:view")
    @RequestMapping(value = {"list", ""})
    public String list(Farmland farmland, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Farmland> page = null;
        User user = UserUtils.getUser();
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        if (!isAdmin) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(FARMER_BOSS)) {
                    farmland.setUser(user);
                }
                if (role.getEnname().equals(FARMER_WORKER)) {
                    farmland.setUsedId(user.getId());
                }
            }
        }
        page = farmlandService.findPage(new Page<Farmland>(request, response), farmland);
        model.addAttribute("page", page);
        return "manager/farmerland/farmlandList";
    }

    @RequiresPermissions("farmerland:farmland:view")
    @RequestMapping(value = "form")
    public String form(Farmland farmland, Model model) {
        if(farmland.getId()!=null&&farmland.getId()!=""){
            Node node=new Node();
            boolean isAdmin=User.isAdmin(UserUtils.getUser().getId());
            if(!isAdmin){
                List<Role> list=UserUtils.getRoleList();
                for(Role role:list){
                    if(role.getEnname().equals(FARMER_BOSS)){
                        node.setUser(UserUtils.getUser());
                    }
                }
            }
            node.setFarmlandId(farmland.getId());
            Page<Node> page=nodeService.findPage(new Page<Node>(),node);
            model.addAttribute("page",page);
        }
        PlantVariety plantVariety=new PlantVariety();
        plantVariety.setCreateBy(UserUtils.getUser());
        List<PlantVariety> plantVarieties=plantVarietyService.findList(plantVariety);
        model.addAttribute("plants",plantVarieties);
        model.addAttribute("farmland", farmland);
        return "manager/farmerland/farmlandForm";
    }

    @RequiresPermissions("farmerland:farmland:edit")
    @RequestMapping(value = "save")
    public String save(Farmland farmland, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, farmland)) {
            return form(farmland, model);
        }
        if(farmland.getId()==null||farmland.getId().equals("")){
            farmland.setUser(UserUtils.getUser());
            farmland.setCreateDate(new Date());
            farmlandService.save(farmland);
        }
      else
        {
            Node node=new Node();
            node.setFarmlandId(farmland.getId());
            List<Node> lists=nodeService.findList(node);
            if(lists.size()>0) {
                for (Node no : lists) {
                    no.setUsedId(farmland.getUsedId());
                    nodeService.save(no);
                }
            }
            if(farmland.getFarmer()==null||farmland.getFarmer().getId().equals("")||farmland.getFarmer().getId()==null){
                farmlandService.updateFarmland(farmland);
            }else {
                farmland.setUser(UserUtils.getUser());
                farmlandService.save(farmland);

            }
        }

        addMessage(redirectAttributes, "保存农田(大棚)成功");
        return "redirect:" + Global.getAdminPath() + "/farmerland/farmland/?repage";
    }

    @RequiresPermissions("farmerland:farmland:edit")
    @RequestMapping(value = "delete")
    public String delete(Farmland farmland, RedirectAttributes redirectAttributes) {
        farmland.setDelFlag("0");
        Node no=new Node();
        no.setFarmlandId(farmland.getId());
        no.setDelFlag("1");
        List<Node> nodeList=nodeService.findList(no);
        if(nodeList!=null&&nodeList.size()>0) {
            for (Node node : nodeList) {
                node.setFarmlandId(null);
                node.setUsedId(null);
                nodeService.save(node);
            }
        }
        farmlandService.delete(farmland);
        addMessage(redirectAttributes, "删除农田(大棚)成功");
        return "redirect:" + Global.getAdminPath() + "/farmerland/farmland/?repage";
    }

    @RequiresPermissions("user")
    @RequestMapping("treeDate")
    @ResponseBody
    public List<Map<String, Object>> treeDate(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Farmland> farmlands = farmlandService.findList(new Farmland());
        System.out.println(farmlands.size() + "sssssssssssssssssss");
        for (Farmland fa : farmlands) {
            Map<String, Object> maps = Maps.newHashMap();
            maps.put("id", fa.getId());
            maps.put("name", StringUtils.replace(fa.getAlias(), " ", ""));
            list.add(maps);
        }
        return list;
    }
    @RequestMapping("/getFarmerById")
    @ResponseBody
    public Map getFarmerById(String id,HttpServletRequest request){
        Map result=Maps.newHashMap();
        Farmland fa=farmlandService.get(id);
        result.put("info",fa);
        return result;
    }
}