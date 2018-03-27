package com.jctl.cloud.api.node;

import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import com.jctl.cloud.utils.QutarzUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by gent on 2017/5/10.
 */
@Controller
@RequestMapping("timing")
public class ANodeTimingConsole {
    @Autowired
    private NodeCollectionCycleService nodeCollectionCycleService;

    /**
     *  获取节点定时策略
     * @param nodeCollectionCycle1
     * @return
     */
    @RequestMapping("getStrategy")
    @ResponseBody
    public Map getStrategy(NodeCollectionCycle nodeCollectionCycle1) {
        Map result = new LinkedHashMap();
        try {
            NodeCollectionCycle nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nodeCollectionCycle1);
            if (nodeCollectionCycle == null) {
                nodeCollectionCycleService.save(nodeCollectionCycle1);
                result.put("flag", "1");
                result.put("msg", "无数据");
            } else {
                List<String> field = new LinkedList();
                field.add("nodeId");
                result.put("flag", "1");
                if (nodeCollectionCycle.getCycleOn() != null){
                    result.put("onWeeks", QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOn()).split(","));
                    nodeCollectionCycle.setCycleOn(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOn()));
                    field.add("cycleOn");
                }
                if (nodeCollectionCycle.getCycleOff() != null){
                    result.put("offWeeks", QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOff()).split(","));
                    nodeCollectionCycle.setCycleOff(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOff()));
                    field.add("cycleOff");
                }
                if (nodeCollectionCycle.getCycleTime() != null){
                    nodeCollectionCycle.setCycleTime(QutarzUtil.getByQutarzStrMin(nodeCollectionCycle.getCycleTime()));
                    field.add("cycleTime");
                }
                for (String prop : field) {
                    result.put(prop, Reflections.invokeGetter(nodeCollectionCycle, prop));
                }
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加周期刷新
     *
     * @param nodeCollectionCycle
     * @return
     */
    @RequestMapping("saveCyclTime")
    @ResponseBody
    public Map getCyclTime(NodeCollectionCycle nodeCollectionCycle) {
        Map result = new HashMap();
        NodeCollectionCycle nodeCollectionCycleTmp = nodeCollectionCycleService.findByNodeId(nodeCollectionCycle);
        try{
            nodeCollectionCycle.setCycleTime("0 */"+nodeCollectionCycle.getCycleTime()+" * * * ?");
            if (nodeCollectionCycleTmp != null){
                nodeCollectionCycleService.updateByNodeId(nodeCollectionCycle);
            }else{
                nodeCollectionCycleService.save(nodeCollectionCycle);
            }
            nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nodeCollectionCycle);
            result.put("flag","1");
            result.put("cycleTime",QutarzUtil.getByQutarzStrMin(nodeCollectionCycle.getCycleTime()));
            QutarzUtil.start();
        }catch (Exception e){
            result.put("flag","0");
            result.put("msg","操作失败");
        }
        return result;
    }

    /**
     * 添加开启策略
     *
     * @param nodeCollectionCycle
     * @return
     */
    @RequestMapping("saveCyclOn")
    @ResponseBody
    public Map saveCyclOn(NodeCollectionCycle nodeCollectionCycle,String[] on) {
        Map result = new HashMap();
        NodeCollectionCycle nodeCollectionCycleTmp = nodeCollectionCycleService.findByNodeId(nodeCollectionCycle);
        try {
            if (nodeCollectionCycleTmp != null) {
                nodeCollectionCycleTmp.setCycleOn(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOn(), on));
                nodeCollectionCycleService.updateByNodeId(nodeCollectionCycleTmp);
            } else {
                nodeCollectionCycleTmp.setCycleOn(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOn(), on));
                nodeCollectionCycleService.save(nodeCollectionCycleTmp);
            }
            nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nodeCollectionCycleTmp);
            result.put("flag", "1");
            result.put("cyclOnTime", QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOn()));
            result.put("cyclOnWeek", QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOn()).split(","));
            result.put("msg","策略设置成功！");
            QutarzUtil.start();
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 添加开启策略
     *
     * @param nodeCollectionCycle
     * @return
     */
    @RequestMapping("saveCyclOff")
    @ResponseBody
    public Map saveCyclOff(NodeCollectionCycle nodeCollectionCycle, String[] off) {
        Map result = new HashMap();
        NodeCollectionCycle nodeCollectionCycleTmp = nodeCollectionCycleService.findByNodeId(nodeCollectionCycle);
        try {
            if (nodeCollectionCycleTmp != null) {
                nodeCollectionCycleTmp.setCycleOff(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOff(), off));
                nodeCollectionCycleService.updateByNodeId(nodeCollectionCycleTmp);
            } else {
                nodeCollectionCycleTmp.setCycleOff(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOff(), off));
                nodeCollectionCycleService.save(nodeCollectionCycleTmp);
            }
            nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nodeCollectionCycleTmp);
            result.put("flag", "1");
            result.put("cyclOnTime", QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOff()));
            result.put("cyclOnWeek", QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOff()).split(","));
            result.put("msg","策略设置成功！");
            QutarzUtil.start();
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }
}
