package com.jctl.cloud.manager.chart;

import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.manager.plant.entity.PlantVariety;
import com.jctl.cloud.manager.plant.service.PlantVarietyService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 图标数据的controller
 *
 * @author lewkay
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/chart")
public class FarmerChartController extends BaseController {


    @Autowired
    private PlantVarietyService plantVarietyService;

    @RequestMapping("farmer")
    public String index(Model model) {


        List<PlantVariety> plants= plantVarietyService.findListByUser(UserUtils.getUser());

        model.addAttribute("plants",plants);
        return "manager/chart/farmerChart";
    }

    @RequestMapping("grow")
    public String grow(Model model){
        return "manager/home/grow";
    }

    @RequestMapping("sysIndex")
    public String sysIndex(){
        return "modules/sys/sysIndex";
    }
}