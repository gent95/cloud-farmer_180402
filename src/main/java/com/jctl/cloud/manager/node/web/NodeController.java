/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.node.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;
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
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.NodeControlUtil;
import com.jctl.cloud.utils.QutarzUtil;

/**
 * 节点管理Controller
 *
 * @author ll
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/node/node")
public class NodeController extends BaseController {


    @Value("#{config['farmerBoss']}")
    private String farmerBoss;

    @Value("#{config['farmerWork']}")
    private String farmerWork;


    @Autowired
    private NodeService nodeService;

    @Autowired
    private NodeDataDetailsService nodeDataDetailsService;

    @Autowired
    private NodeControlUtil dateResultUtil;
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private RelayService relayService;
    @Autowired
    private WaringCycleService waringCycleService;

    @Autowired
    private NodeCollectionCycleService nodeCollectionCycleService;


    @ModelAttribute
    public Node get(@RequestParam(required = false) String id) {
        Node entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = nodeService.get(id);
        }
        if (entity == null) {
            entity = new Node();
        }
        return entity;
    }

    @RequiresPermissions("node:node:view")
    @RequestMapping(value = {"list", ""})
    public String list(Node node, HttpServletRequest request, HttpServletResponse response, Model model) {
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        Page<Node> page = null;
        try {
        if (isAdmin) {
            page = nodeService.findPage(new Page<Node>(request, response), node);
        } else {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(farmerBoss)) {
                    node.setUser(UserUtils.getUser());
                    List<Relay> relays = relayService.findList(new Relay(UserUtils.getUser()));
                    node.setRelays(relays);
                }
                if(role.getEnname().equals(farmerWork)){
                    node.setUsedId(UserUtils.getUser().getId());
                }
            }

            page = nodeService.findPage(new Page<Node>(request, response), node);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("page", page);
        return "manager/node/nodeList";
    }
    @RequestMapping("nodelist")
    @ResponseBody
    public Map nodeList(Node node) {
        Map result = Maps.newHashMap();
        List lists = new ArrayList();
        List<Node> nodeList=null;
        try {
            boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
            if (isAdmin) {
               nodeList = nodeService.findList(node);
            }else {
                List<Relay> relays = relayService.findList(new Relay(UserUtils.getUser()));
                node.setRelays(relays);
                nodeList = nodeService.findList(node);
            }
            if (nodeList != null || nodeList.size() > 0) {
                String[] proper = new String[]{"id", "nodeNum", "farmlandName", "usedName", "power", "nodeAlise", "onOffName","openFlag"};
                for (Node no : nodeList) {
                    Map maps = Maps.newHashMap();
                    for (String property : proper) {
                        maps.put(property, Reflections.invokeGetter(no, property));
                    }
                    lists.add(maps);
                }
                result.put("flag", 1);
                result.put("info", lists);
            } else {
                result.put("flag", 0);
                result.put("msg", "抱歉未查询到数据");
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }


    @RequiresPermissions("node:node:view")
    @RequestMapping(value = "form")
    public String form(Node node, Model model) {
        Farmland farmland = new Farmland();
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        Relay relay = null;
        if (!isAdmin) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(farmerBoss)) {
                    farmland.setUser(UserUtils.getUser());
                }
            }
        }
        if (node.getRelayName() != null && "".equals(node.getRelayName())) {
            relay = relayService.getByMac(node.getRelayName());
            if (relay.getFarmer() != null && relay.getFarmer().getId() != null && !relay.getFarmer().getId().equals("")) {
                farmland.setFarmer(relay.getFarmer());
            }
        }
        List<Farmland> lists = farmlandService.findList(farmland);
        model.addAttribute("node", node);
        model.addAttribute("relay", relay);
        model.addAttribute("lists", lists);
        return "manager/node/nodeForm";
    }

    @RequiresPermissions("node:node:edit")
    @RequestMapping(value = "save")
    public String save(Node node, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, node)) {
            return form(node, model);
        }

        if (node.getRelayId() != null) {
            if (node.getFarmlandId() != null && !"".equals(node.getFarmlandId()) && node.getFarmlandId() != "-1") {
                Farmland farmland = new Farmland();
                farmland.setId(node.getFarmlandId());
                Relay re = relayService.get(node.getRelayId().toString());
                if (re != null) {
                    if (re.getFarmer() != null) {
                        if (re.getFarmer().getId() != null && re.getFarmer().getId() != "") {
                            farmland.setFarmer(re.getFarmer());
                            farmland.setRelay(re);
                            farmlandService.updateById(farmland);
                        }
                    }
                }
                Farmland fa = farmlandService.get(node.getFarmlandId());
                if (fa != null) {
                    if (fa.getUsedId() != null && !"".equals(fa.getUsedId())) {
                        node.setUsedId(farmlandService.get(node.getFarmlandId().toString()).getUsedId());
                    }
                } else {
                    node.setUsedId("");
                }
            }

        }
        nodeService.save(node);
        addMessage(redirectAttributes, "保存节点成功");
        return "redirect:" + Global.getAdminPath() + "/node/node/?repage";
    }

    @RequiresPermissions("node:node:edit")
    @RequestMapping(value = "delete")
    public String delete(Node node, RedirectAttributes redirectAttributes) {
        node.setDelFlag("0");
        nodeService.delete(node);
        addMessage(redirectAttributes, "删除节点成功");
        return "redirect:" + Global.getAdminPath() + "/node/node/?0";
    }

    /**
     * 验证新增验证是否重复
     *
     * @param node
     * @return
     */
    @RequestMapping(value = "fetechNode")
    public
    @ResponseBody
    int fetechtionNode(Node node) {
        List<Node> nodeList = nodeService.findAllByNum(node);
        return nodeList.size();
    }

    /**
     * 立即刷新当前节点各项信息
     *
     * @param node node对象必须存在当前node的id
     * @return
     */
    @RequestMapping("/refreshNodeInfo")
    @ResponseBody
    public Map<String, Object> refreshNodeInfo(Node node) {
        Map<String, Object> result = new HashMap<>();
        try {
            dateResultUtil.refreshNodeByNodeId(node);
            result.put("status", 1);//刷新成功
            result.put("msg", "操作成功");//刷新成功
        } catch (Exception e) {
            result.put("msg", "操作失败");
            result.put("status", 0);
        }
        return result;
    }

    /**
     * @param node
     * @param model
     * @return
     */

    @RequestMapping("/strategyManagerment")
    public String strategyManagerment(Node node, Model model) {
        NodeCollectionCycle nCC = new NodeCollectionCycle();
        Node nodetmp = nodeService.get(node.getId());
        nCC.setNodeId(nodetmp.getNodeNum());
        try {
            NodeCollectionCycle nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nCC);
            String min = "";
            if ( nodeCollectionCycle.getCycleTime() != null && !nodeCollectionCycle.getCycleTime().equals("")) {
                min = QutarzUtil.getByQutarzStrMin(nodeCollectionCycle.getCycleTime());
            }
            model.addAttribute("min", min);
            Map<String, String[]> weeks = new HashMap<>();
            if (nodeCollectionCycle.getCycleOn() != null && !nodeCollectionCycle.getCycleOn().equals("")){
                weeks.put("on", QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOn()).split(","));
            }
           if (nodeCollectionCycle.getCycleOff() != null && !nodeCollectionCycle.getCycleOff().equals("")){
               weeks.put("off", QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOff()).split(","));
           }
            model.addAttribute("weeks", weeks);
            WaringCycle waringCycle = new WaringCycle();
            waringCycle.setNodeNum(nodetmp.getNodeNum());
            List<WaringCycle> list = waringCycleService.findList(waringCycle);
            if (nodeCollectionCycle.getCycleTime() != null && !nodeCollectionCycle.getCycleTime().equals("")){
                nodeCollectionCycle.setCycleTime(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleTime()));
            }
            if (nodeCollectionCycle.getCycleOn()!= null&& !nodeCollectionCycle.getCycleOn().equals("")){
                nodeCollectionCycle.setCycleOn(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOn()));
            }
            if (nodeCollectionCycle.getCycleOff() != null && !nodeCollectionCycle.getCycleOff().equals("")){
                nodeCollectionCycle.setCycleOff(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOff()));
            }
            model.addAttribute("waringCycles", list);
            model.addAttribute("nodeCollectionCycle", nodeCollectionCycle);
            model.addAttribute("node", nodetmp);
            QutarzUtil.start();
        } catch (Exception e) {
           System.out.println(node.getNodeNum()+"节点的定时策略为空，清初始化一个！");
        }
        return "manager/node/strategy";
    }

    /**
     * 修改节点开闭状态
     *
     * @param node
     * @return
     */
    @RequestMapping("changeSwitch")
    @ResponseBody
    public Map<String, String> changeSwitch(Node node) {
        Map<String, String> result = new HashMap<>();
        NodeControlUtil nodeControlUtil = new NodeControlUtil();

        try {
            node.setUpdateTime(new Date());
            nodeService.save(node);
            if (node.getTask().equals("0")) {
                nodeControlUtil.closeNode(node);
            }
            if (node.getTask().equals("1")) {
                nodeControlUtil.openNode(node);
            }
            result.put("flag", "1");
            QutarzUtil.start();
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "设备掉线，或未连接！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询节点是否正常关闭
     *
     * @param node
     * @return
     */
    @RequestMapping("selectSwitch")
    @ResponseBody
    public Map<String, String> selectSwitch(Node node) {
        Map<String, String> result = new HashMap<>();
        String task = node.getTask();
        try {
            node = nodeService.get(node);
            if (task.equals("0")) {
                if (node.getOpenFlag() != null && node.getOpenFlag().equals("0")) {
                    result.put("flag", "2");
                    result.put("msg", "操作成功！");
                    return result;
                }
            }
            if (task.equals("1")) {
                if (node.getOpenFlag() != null && node.getOpenFlag().equals("1")) {
                    result.put("flag", "2");
                    result.put("msg", "操作成功！");
                    return result;
                }
            }
            boolean flag = (new Date().getTime() - node.getUpdateTime().getTime()) >= 50 * 1000;
            if (flag) {
                result.put("flag", "0");
                result.put("msg", "网络连接超时！");
            } else {
                result.put("flag", "1");
                result.put("msg", "正在执行操作中，请稍后...");
            }
        } catch (Exception e) {
            result.put("flag", "0");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 手动开关控制
     *
     * @param id
     * @param task
     * @param model
     * @return
     */
    @RequestMapping("manual")
    public String manual(String id, String task, Model model, RedirectAttributes redirectAttributes) {
        Node node = nodeService.get(id);
        NodeControlUtil nodeControlUtil = new NodeControlUtil();
        Map<String, Object> result = Maps.newHashMap();
        try {
            if (task.equals("0")) {
                //执行关闭命令
                nodeControlUtil.closeNode(node);
            } else if (task.equals("1")) {
                //执行开启命令
                nodeControlUtil.openNode(node);
            }
        } catch (Exception e) {
            node.setOpenFlag("-1");
            System.out.print(node.getNodeNum() + "开关指令执行失败");
        } finally {
            model.addAttribute("node", node);
            return strategyManagerment(node, model);
        }
    }

    @RequestMapping("/banUsed")
    public
    @ResponseBody
    Map<String, Object> banUsed(Node node, Model model) {

        Map<String, Object> result = Maps.newHashMap();
        try {
            nodeService.save(node);
            result.put("status", 1);//禁止使用成功
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("status", 0);//禁止使用失败
            result.put("msg", "操作成功");
        }
        return result;
    }

    /**
     * 查询最后一条节点数据
     *
     * @param node
     * @return
     */
    @RequestMapping("selectNode")
    @ResponseBody
    public Map selectNode(Node node) {
        Map result = new HashMap();
        try {
            Node node1 = nodeService.get(node);

            NodeDataDetails details = nodeDataDetailsService.getLastByNodeNum(node1.getNodeNum());
            Long time = System.currentTimeMillis() - details.getAddTime().getTime();
            if (time <= 5 * 1000L) {
                result.put("flag", 1);
            } else {
                result.put("flag", 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("flag", 0);
        }
        return result;
    }
}