package com.jctl.cloud.api.node;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.common.utils.excel.annotation.ExcelField;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.FrontUserUtils;
import com.jctl.cloud.utils.NodeControlUtil;
import com.jctl.cloud.utils.QutarzUtil;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 马江涛 on 2017/3/27.
 */
@Controller
@RequestMapping("node")
public class ANodeController {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private NodeDataDetailsService nodeDataDetailsService;
    @Autowired
    private WaringCycleService waringCycleService;
    @Autowired
    private NodeCollectionCycleService nodeCollectionCycleService;
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private RelayService relayService;

    /**
     * 节点详情列表
     *
     * @param
     * @return
     */
    @RequestMapping("detailList")
    @ResponseBody
    public Map listNodeDetail(String farmlandId) {
        Map result = Maps.newHashMap();
        List lists = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<NodeDataDetails> nodeDataDetailsLists = nodeDataDetailsService.findNodeDetailByFarmlandId(farmlandId);
            if (nodeDataDetailsLists != null || nodeDataDetailsLists.size() > 0) {
                for (NodeDataDetails dataDetails : nodeDataDetailsLists) {
                    String[] proper = new String[]{"id", "nodeMac", "airTemperature", "airHumidity", "soilTemperature1", "soilHumidity1", "soilTemperature2", "soilHumidity2", "soilTemperature3", "soilHumidity3", "co2", "openFlag", "power", "addTime", "nodeName"};
                    Map maps = Maps.newHashMap();
                    for (String property : proper) {
                        if (property == "addTime") {
                            maps.put(property, sdf.format(dataDetails.getAddTime()));
                        } else {
                            maps.put(property, Reflections.invokeGetter(dataDetails, property));
                        }
                    }
                    lists.add(maps);
                }
                result.put("flag", "1");
                result.put("info", lists);
            } else {
                result.put("flag", "0");
                result.put("msg", "抱歉未查询到数据");
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }

    /**
     * 节点列表进去的节点详情数据
     *
     * @param
     * @return
     */
    @RequestMapping("nodeDetailList")
    @ResponseBody
    public Map nodeDetailList(String nodeId) {
        Map result = Maps.newHashMap();
        List lists = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NodeDataDetails nodeDataDetails=new NodeDataDetails();
        try {
            Node node = nodeService.get(nodeId);
            nodeDataDetails.setNodeMac(node.getNodeNum());
            List<NodeDataDetails> nodeDataDetailsLists = nodeDataDetailsService.findList(nodeDataDetails);
            if (nodeDataDetailsLists != null || nodeDataDetailsLists.size() > 0) {
                for (NodeDataDetails dataDetails : nodeDataDetailsLists) {
                    String[] proper = new String[]{"addTime","airTemperature", "airHumidity", "soilTemperature1", "soilHumidity1", "soilTemperature2", "soilHumidity2", "soilTemperature3", "soilHumidity3", "co2", "openFlag", "power","id", "nodeMac","nodeName"};
                    Map maps = Maps.newHashMap();
                    for (String property : proper) {
                        if (property == "addTime") {
                            maps.put(property, sdf.format(dataDetails.getAddTime()));
                        }else if(property=="nodeName"){
                            maps.put(property,node.getNodeAlise());
                        } else {
                            maps.put(property, Reflections.invokeGetter(dataDetails, property));
                        }
                    }
                    lists.add(maps);
                }
                result.put("flag", "1");
                result.put("info", lists);
            } else {
                result.put("flag", "0");
                result.put("msg", "抱歉未查询到数据");
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }

    /**
     * 节点管理
     */
    @RequestMapping("list")
    @ResponseBody
    public Map nodeList(Node node) {
        Map result = Maps.newHashMap();
        List lists = new ArrayList();
        try {
            node.preSearch();
            List<Node> nodeList = nodeService.findListAll(node);
            if (nodeList != null || nodeList.size() > 0) {
                String[] proper = new String[]{"id", "nodeNum", "farmlandName", "usedName", "power", "nodeAlise", "onOffName"};
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

    /**
     * 节点详情
     */
    @RequestMapping("get")
    @ResponseBody
    public Map getNode(Node node) {
        Map result = Maps.newHashMap();
        try {

            Node no = nodeService.get(node);
            String[] proper = new String[]{"id", "nodeNum", "type", "user", "usedName", "relayName", "cycle", "addTime", "farmlandName", "power", "nodeAlise", "onOffName"};
            Map info = Maps.newHashMap();
            for (String property : proper) {
                if (property.equals("relayName")) {
                    info.put("relayNum", Reflections.invokeGetter(no, property));
                } else {
                    info.put(property, Reflections.invokeGetter(no, property));
                }
                if (property.equals("addTime")) {
                    if (no.getAddTime() != null) {
                        info.put(property, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(no.getAddTime()));
                    } else {
                        info.put(property, "");
                    }
                }
            }

            NodeDataDetails search = new NodeDataDetails();
            search.setNodeMac((String) info.get("nodeNum"));
            NodeDataDetails dataDetails = nodeDataDetailsService.findDetail(search);
            String[] property1 = new String[]{"nodeMac", "airTemperature", "airHumidity", "soilTemperature1", "soilHumidity1", "soilTemperature2", "soilHumidity2", "soilTemperature3", "soilHumidity3", "co2", "openFlag", "power", "addTime"};
            Map data = Maps.newHashMap();
            for (String property : property1) {
                if(property.equals("addTime")){
                    data.put(property, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataDetails.getAddTime()));
                }else {
                    if (property.equals("openFlag")) {
                        data.put(property, no.getOpenFlag());
                    } else {
                        data.put(property, Reflections.invokeGetter(dataDetails, property));
                    }
                }
            }
            Farmland query = new Farmland();
            query.preSearch();
            List<Farmland> lands = farmlandService.findList(query);

            result.put("flag", 1);
            result.put("data", data);
            result.put("lands", lands);
            result.put("node", info);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("lastNodeDetails")
    @ResponseBody
    public Map<String, Object> lastNodeDetails(Node node) {
        Map<String, Object> result = new HashMap<>();
        try {
            NodeDataDetails details = nodeDataDetailsService.lastNodeDetails(node);
            details.setOpenFlag(nodeService.get(node).getOpenFlag());
            result.put("flag", 1);
            result.put("info", details);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public Map updateNode(Node node, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            nodeService.save(node);
            result.put("flag", 1);
            result.put("msg", "修改成功");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "修改失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("strategyManagerment")
    @ResponseBody
    public Map strategyManagerment(String nodeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM:dd HH:mm:ss");
        Map result = Maps.newHashMap();
        List data = new ArrayList();
        NodeCollectionCycle nCC = new NodeCollectionCycle();
        Node node = nodeService.get(nodeId);
        nCC.setNodeId(nodeId);
        try {
            NodeCollectionCycle nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nCC);
            if (nodeCollectionCycle != null || nodeCollectionCycle.getCycleOn() != "" || nodeCollectionCycle.getCycleOff() != "" || nodeCollectionCycle.getCycleTime() != "") {
                List<WaringCycle> list = waringCycleService.findList(new WaringCycle());
                String[] property = new String[]{"id", "nodeNum", "property", "max", "min", "cycle"};
                for (WaringCycle w : list) {
                    Map maps = Maps.newHashMap();
                    for (String pro : property) {
                        maps.put(pro, Reflections.invokeGetter(w, pro));

                    }
                    data.add(maps);
                }
                List nodeInfo = new ArrayList();
                String[] property1 = new String[]{"id", "nodeNum"};
                Map mapNode = Maps.newHashMap();
                for (String pro1 : property1) {
                    mapNode.put(pro1, Reflections.invokeGetter(node, pro1));
                }
                nodeInfo.add(mapNode);
                List nodeCollectiontype = new ArrayList();
                Map nodeColMap = Maps.newHashMap();
                String[] property2 = new String[]{"id", "nodeId", "cycleTime", "cycleOff", "cycleOn", "addTime", "updateTime"};
                for (String pro2 : property2) {
                    if (pro2 == "addTime") {
                        nodeColMap.put(pro2, sdf.format(nodeCollectionCycle.getAddTime()));
                    } else if (pro2 == "updateTime") {
                        nodeColMap.put(pro2, sdf.format(nodeCollectionCycle.getUpdateTime()));
                    } else {
                        nodeColMap.put(pro2, Reflections.invokeGetter(nodeCollectionCycle, pro2));
                    }
                }
                nodeCollectiontype.add(nodeColMap);
                result.put("flag", "1");
                result.put("nodeInfo", nodeInfo);
                result.put("cycleList", data);
                result.put("nodeCycle", nodeCollectiontype);
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "没有这个节点对应的开关策略");
        }
        return result;
    }

    /**
     * 开关控制
     *
     * @return
     */
    @RequestMapping("HandOpenClose")
    @ResponseBody
    public Map HandOpenClose(Node node) {
        Map result = Maps.newHashMap();
        NodeControlUtil nodeControlUtil = new NodeControlUtil();
        try {
            if (node.getStatus().equals("0")) {
                nodeControlUtil.closeNode(node);
            }
            if (node.getStatus().equals("1")) {
                nodeControlUtil.openNode(node);
            }
            Long time = System.currentTimeMillis();
            Node res;
            for (; ; ) {
                Thread.sleep(3 * 1000);
                res = nodeService.get(node);
                if ((System.currentTimeMillis() - res.getUpdateTime().getTime()) <= 5 * 1000) {
                    result.put("flag", "1");
                    result.put("msg", "操作成功！");
                    break;
                }
                if ((System.currentTimeMillis() - time) > 30 * 1000) {
                    result.put("flag", "0");
                    result.put("msg", "连接超时！");
                    break;
                }
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "设备掉线，或未连接！");
        }
        return result;
    }

    /**
     * 智能控制列表
     */
    @RequestMapping("cycleList")
    @ResponseBody
    public Map cycleList(WaringCycle waringCycle) {
        Map result = Maps.newHashMap();
        try {
            List<WaringCycle> list = waringCycleService.findList(waringCycle);
            if (list == null || list.size() == 0) {
                result.put("flag", "0");
                result.put("msg", "暂无数据");
            }
            String[] propertys = new String[]{"id", "nodeNum", "property", "max", "min", "cycle"};
            List lists = new ArrayList();
            for (WaringCycle cycle : list) {
                Map maps = Maps.newHashMap();
                for (String pro : propertys) {
                    if (pro.equals("property")) {
                        String param = "";
                        if (cycle.getProperty() == null || cycle.getProperty().equals("")) {
                            continue;
                        }
                        switch (cycle.getProperty()) {
                            case "airTemperature":
                                param = "空气温度";
                                break;
                            case "airHumidity":
                                param = "空气湿度";
                                break;
                            case "soilTemperature1":
                                param = "一号采集点温度";
                                break;
                            case "soilHumidity1":
                                param = "一号采集点湿度";
                                break;
                            case "soilTemperature2":
                                param = "二号采集点温度";
                                break;
                            case "soilHumidity2":
                                param = "二号采集点湿度";
                                break;
                            case "soilTemperature3":
                                param = "三号采集点温度";
                                break;
                            case "soilHumidity3":
                                param = "三号采集点湿度";
                                break;
                            case "co2":
                                param = "二氧化碳浓度";
                                break;
                        }
                        maps.put(pro, param);
                    } else {
                        maps.put(pro, Reflections.invokeGetter(cycle, pro));
                    }
                }
                lists.add(maps);
            }
            result.put("flag", "1");
            result.put("info", lists);
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 智能控制列表
     */
    @RequestMapping("cycleUpdate")
    @ResponseBody
    public Map cycleUpdate(WaringCycle waringCycle) {
        Map result = Maps.newHashMap();
        try {
            if (waringCycle.getId() == null || waringCycle.getId().equals("")) {
                result.put("flag", "0");
                result.put("msg", "操作失败");
            }
            waringCycle.setProperty(waringCycleService.get(waringCycle).getProperty());
            waringCycleService.save(waringCycle);
            result.put("flag", "1");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 智能控制列表
     */
    @RequestMapping("cycleDel")
    @ResponseBody
    public Map cycleDel(WaringCycle waringCycle) {
        Map result = Maps.newHashMap();
        try {
            waringCycleService.delete(waringCycle);
            result.put("flag", "1");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }


    /**
     * 详情数据表
     */
    @RequestMapping("getByDay")
    @ResponseBody
    public Map getNodeDtailsList(String nodeNum, String type, String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Map result = new HashMap();
        String param = null;
        try {
            Map map = new HashMap();
            map.put("nodeMac", "'" + nodeNum + "'");
            switch (type) {
                case "airTemperature":
                    param = "air_temperature";
                    break;
                case "airHumidity":
                    param = "air_humidity";
                    break;
                case "soilTemperature1":
                    param = "soil_temperature1";
                    break;
                case "soilHumidity1":
                    param = "soil_humidity1";
                    break;
                case "soilTemperature2":
                    param = "soil_temperature2";
                    break;
                case "soilHumidity2":
                    param = "soil_humidity2";
                    break;
                case "soilTemperature3":
                    param = "soil_temperature3";
                    break;
                case "soilHumidity3":
                    param = "soil_humidity3";
                    break;
                case "co2":
                    param = "co2";
                    break;
            }
            map.put("param", param);
            if (data == null || data.equals("")) {
                data = sdf1.format(new Date());
            }
            map.put("addTime", "'" + data + "'");
            List<NodeDataDetails> lists = nodeDataDetailsService.findByDay(map);
            List info = new ArrayList();
            NodeDataDetails dataDetails = null;
            if (lists != null && lists.size() > 0) {
                String[] proper = new String[]{"content", "addTime"};
                Map maps = Maps.newHashMap();
                String[] addTimes = new String[lists.size()];
                String[] valuePare = new String[lists.size()];
                for (String property : proper) {
                    for (int i = 0; i < lists.size(); i++) {
                        dataDetails = lists.get(i);
                        if (property == "addTime") {
                            addTimes[i] = sdf.format(dataDetails.getAddTime());
                        } else {
                            valuePare[i] = String.valueOf(Reflections.invokeGetter(dataDetails, property));
                        }
                    }
                    if (property == "addTime") {
                        maps.put(property, addTimes);
                    } else {
                        maps.put(property, valuePare);
                    }

                }
                info.add(maps);
                result.put("info", info);
            } else {
                result.put("msg", "暂无数据");
            }
            result.put("flag", 1);

        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
        }
        return result;
    }

    /**
     * 周期控制进入页面
     */
    @RequestMapping("getcycleMsg")
    @ResponseBody
    public Map getCycle(String nodeNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Map result = Maps.newHashMap();
        List lists = new ArrayList();
        try {
            NodeCollectionCycle nCC = new NodeCollectionCycle();
            nCC.setNodeId(nodeNum);
            NodeCollectionCycle nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nCC);
            if (nodeCollectionCycle != null) {
                String[] property = new String[]{"id", "nodeId", "cycleTime", "cycleOff", "cycleOn", "addTime", "updateTime"};
                Map maps = Maps.newHashMap();
                for (String pro : property) {
                    if (pro.equals("addTime")) {
                        maps.put(pro, sdf.format(nodeCollectionCycle.getAddTime()));
                    } else if (pro.equals("updateTime")) {
                        maps.put(pro, sdf.format(nodeCollectionCycle.getUpdateTime()));
                    } else if (pro.equals("cycleTime")) {
                        String cycTime = QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleTime());
                        maps.put(pro, cycTime);
                    } else if (pro.equals("cycleOff")) {//关闭策略
                        String cycleOff = QutarzUtil.qutarzStrConvertDate(nodeCollectionCycle.getCycleOff());
                        maps.put(pro, cycleOff);
                    } else if (pro.equals("cycleOn")) {//开启策略
                        String cycleOn = QutarzUtil.qutarzStrConvertDate(nodeCollectionCycle.getCycleOn());
                        maps.put(pro, cycleOn);
                    } else {
                        maps.put(pro, Reflections.invokeGetter(nodeCollectionCycle, pro));
                    }
                }
                lists.add(maps);
                result.put("flag", 1);
                result.put("info", lists);
            } else {
                result.put("flag", 0);
                result.put("msg", "没有这个节点对应的周期策略");
            }

        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
        }
        return result;
    }

    /**
     * 周期控制
     *
     * @return
     */
    @RequestMapping("cycleMsg")
    @ResponseBody
    public Map cycle(NodeCollectionCycle nodeCollectionCycle, String nodeNum, String[] off, String[] on) {
        Map result = Maps.newHashMap();
        try {
            Node node = nodeService.findByNodeNum(nodeNum);
            nodeCollectionCycle.setNodeId(node.getId());
            nodeCollectionCycleService.deleteByNodeId(nodeCollectionCycle);
            nodeCollectionCycle.setAddUserId(Long.parseLong(UserUtils.getUser().getId()));
            nodeCollectionCycle.setCycleTime(0 + " " + nodeCollectionCycle.getCycleTime() + " * * * ?");
            nodeCollectionCycle.setCycleOn(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOn()));
            nodeCollectionCycle.setCycleOff(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOff(), off));
            nodeCollectionCycleService.save(nodeCollectionCycle);
            //启动定时器
            QutarzUtil.start();
            result.put("flag", 1);
            result.put("msg", "设置成功");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
        }
        return result;
    }

    /**
     * 智能控制
     *
     * @return
     */
    @RequestMapping("autoMsg")
    @ResponseBody
    public Map autoNode(WaringCycle waringCycle) {
        Map result = Maps.newHashMap();
        try {
            if (isExist(waringCycle)) {
                result.put("flag", 0);
                result.put("msg", "已存在标准，不能重复添加！");
            }
            waringCycleService.save(waringCycle);
            result.put("flag", 1);
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    private boolean isExist(WaringCycle waringCycle) {
        List list = waringCycleService.findList(waringCycle);
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @RequestMapping("updateAutoMsg")
    @ResponseBody
    public Map autoMsg(String id) {
        Map result = Maps.newHashMap();
        try {
            WaringCycle waringCycle = waringCycleService.get(id);
            String[] property = new String[]{"id", "nodeNum", "property", "max", "min", "cycle"};
            List lists = new ArrayList();
            Map maps = Maps.newHashMap();
            for (String pro : property) {
                maps.put(pro, Reflections.invokeGetter(waringCycle, pro));
            }
            lists.add(maps);
            result.put("flag", 1);
            result.put("info", lists);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }
}
