package com.jctl.cloud.manager.grow;

import com.jctl.cloud.manager.datection.entity.Datection;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.grow.service.WeatherStationService;
import com.jctl.cloud.manager.plant.entity.PlantVariety;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.manager.weather.entity.Weather;
import com.jctl.cloud.manager.weather.service.WeatherService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by gent on 2017/5/17. ${adminPath}/grow
 */
@Controller
@RequestMapping("${adminPath}/grow")
public class GrowContoller {

    @Autowired
    private RelayService relayService;

    @Autowired
    private FarmlandService farmlandService;

    @Autowired
    private FarmerService farmerService;

//    @Autowired
//    private WeatherStationService weatherStationService;

    @Autowired
    private WeatherService weatherService;

    private List plantSes;

    /**
     * 获取当前用户所属的农场
     *
     * @param farmer userId
     * @return
     */
    @RequestMapping("getFarmers")
    @ResponseBody
    public Map getFarmers(Farmer farmer) {
        Map result = new LinkedHashMap();
        farmer.setUser(UserUtils.getUser());
//        List<Farmer> farmers = farmerService.findList(new Farmer());
        List<Farmer> farmers = new ArrayList<>();
        for (Role role : UserUtils.getRoleList()) {
            if (role.getEnname().equals("dept")) {
                farmers = farmerService.findList(new Farmer());
            } else {
                farmers = farmerService.findFarmerByUserId(farmer);
            }
        }
        List<Relay> relays = relayService.findRelayNumByFarmerId(farmer.getId());
        result.put("farmers", farmers);
        result.put("relays", relays);
        return result;
    }

    /**
     * 获取八个整点时刻的气象数据
     *
     * @return
     */
    @RequestMapping("getDatectionDates")
    @ResponseBody
    public Map getDatectionDates() {
            Map result = new HashMap();
            List<Weather> weathers = weatherService.getTop8Data();
            List<String> winSpeed = new LinkedList<>();
            List<String> airTemperature = new LinkedList<>();
            List<String> humidity = new LinkedList<>();
            List<String> rainV = new LinkedList<>();
            List<String> radiate = new LinkedList<>();
            List<String> windDirection = new LinkedList<>();
            List<String> evaporation = new LinkedList<>();
            List<String> date = new LinkedList<>();
            for (int i = 0; i < weathers.size(); i++) {
                winSpeed.add(weathers.get(i).getWindSpeed());
                airTemperature.add(weathers.get(i).getTemperature());
                humidity.add(weathers.get(i).getHumidity());
                rainV.add(weathers.get(i).getRainfall());
                radiate.add(weathers.get(i).getRadiate());
                windDirection.add(weathers.get(i).getWindDirection());
                evaporation.add(weathers.get(i).getPhotosynthetically());
                date.add(weathers.get(i).getAddTime().toString().split(" ")[3]);
            }
            result.put("windSpeed", winSpeed);
            result.put("airTemperature", airTemperature);
            result.put("humidity", humidity);
            result.put("rainV", rainV);
            result.put("radiate", radiate);
            result.put("windDirection", windDirection);
            result.put("evaporation", evaporation);
            result.put("dateTime", date);
        return result;
    }

    /**
     * 获取指定农场下中继和节点数量 farmerId
     *
     * @return
     */
    @RequestMapping("getNodeAndRelayCount")
    @ResponseBody
    public Map getNodeAndRelayCount(Farmer farmer) {
        Map result = new HashMap();

        List<Farmer> farmers = farmerService.findList(farmer);
        int relayCount = 0;
        for (Farmer tmpFarmer : farmers) {
            relayCount = relayCount + tmpFarmer.getRelayNumber();
        }
        result.put("relayCount", relayCount);

        Farmland farmland = new Farmland();
        farmland.setFarmer(farmer);
        List<Farmland> list = farmlandService.findList(farmland);
        int nodeCount = 0;
        for (Farmland tmpFarmerLand : list) {
            tmpFarmerLand.getNodeNumber();
            nodeCount = nodeCount + tmpFarmerLand.getNodeNumber();
        }
        result.put("nodeCount", nodeCount);
        return result;
    }

    /**
     * 获取指定农场下大棚和农田的数量
     *
     * @param farmland farmerId
     * @return
     */
    @RequestMapping("getByFarmerlandTypeCount")
    @ResponseBody
    public Map getByFarmerlandTypeCount(Farmland farmland) {
        Map result = new HashMap();
        farmland.setCreateBy(UserUtils.getUser());
        result.put("peng", farmlandService.findPengCount(farmland));
        result.put("tian", farmlandService.findTianCount(farmland));

        List<Farmland> farmlands = farmlandService.findPlantTypeCountByFarmerId(farmland);
        int usedCount = 0;
        int userCount = 0;


        List<Map<String, Object>> plant = new ArrayList();
        String[] propertys = new String[]{"value", "name"};
        Set<String> users = new HashSet<>();
        for (Farmland tmpFarmland : farmlands) {
            if (tmpFarmland.getPlantVaritety() != null && !tmpFarmland.getPlantVaritety().equals("")) {
                Map map = new HashMap();
                for (String property : propertys) {
                    if (property == "value") {
                        map.put(property, tmpFarmland.getPlantNum());
                    } else {
                        map.put(property, tmpFarmland.getPlantVaritety());
                    }
                }
                plant.add(map);
            }
            if (tmpFarmland.getUsedId() != null && !tmpFarmland.getUsedId().equals("")) {
                usedCount++;
            }
            if (tmpFarmland.getUser().getId() != null && !tmpFarmland.getUser().getId().equals("")) {
                users.add(tmpFarmland.getUser().getId());
            }
        }
        userCount = users.size();
        if (userCount == 0) {
            userCount = 1;
        }

        result.put("plant", plant);
        result.put("userCount", userCount);
        result.put("usedCount", usedCount);
        return result;
    }

    /**
     * 获取中继坐标作为农场坐标
     *
     * @param relay
     * @return
     */
    @RequestMapping("getLogAndLat")
    @ResponseBody
    public Map getLogAndLat(Relay relay) {
        Map result = new HashMap();
        Relay tmpRelay = relayService.getByFamerId(relay.getFarmerId().toString());
        result.put("lng", tmpRelay.getLog());
        result.put("lat", tmpRelay.getLat());
        return result;
    }

    @RequestMapping("getOne")
    public String goToOne(String parms) {
        System.out.println(parms + "---------------------");
        Map result1 = new HashMap();
        if (parms.equals("成都市")) {
            result1.put("花卉", "30");
            result1.put("油菜", "136.7");
            result1.put("水稻", "115");
            result1.put("小麦", "55");
            result1.put("水果", "106");
            result1.put("茶叶", "16");
            result1.put("蔬菜", "265");
            result1.put("洋芋", "27");
            result1.put("川芎", "5");
        } else if (parms.equals("自贡市")) {
            result1.put("油菜", "9.5");
            result1.put("水稻", "80");
            result1.put("水果", "9.58");
            result1.put("茶叶", "3");
            result1.put("蔬菜", "0.52");
            result1.put("洋芋", "5.4");
            result1.put("大豆", "40");
            result1.put("玉米", "49");
        } else if (parms.equals("泸州市")) {
            result1.put("油菜", "12.45");
            result1.put("水稻", "237.15");
            result1.put("水果", "86.7");
            result1.put("茶叶", "10.45");
            result1.put("蔬菜", "67.5");
            result1.put("大豆", "15");
            result1.put("玉米", "75");
            result1.put("烤烟", "28.5");
        } else if (parms.equals("德阳市")) {
            result1.put("油菜", "87.5");
            result1.put("水稻", "186.45");
            result1.put("小麦", "111");
            result1.put("蔬菜", "1.74");
            result1.put("洋芋", "7.7");
            result1.put("中药", "10.05");
            result1.put("玉米", "20.22");
        } else if (parms.equals("绵阳市")) {
            result1.put("油菜", "10");
            result1.put("水稻", "201");
            result1.put("小麦", "215");
            result1.put("蔬菜", "48");
            result1.put("洋芋", "88");
            result1.put("玉米", "142.3");
            result1.put("棉花", "31.72");
            result1.put("花生", "9.4");
            result1.put("红苔", "34.61");
        } else if (parms.equals("广元市")) {
            result1.put("水稻", "83.6");
            result1.put("小麦", "129");
            result1.put("水果", "100");
            result1.put("茶叶", "17");
            result1.put("蔬菜", "55");
            result1.put("玉米", "104.3");
        } else if (parms.equals("遂宁市")) {
            result1.put("油菜", "64.4");
            result1.put("水稻", "135");
            result1.put("小麦", "112");
            result1.put("水果", "12");
            result1.put("蔬菜", "46.3");
            result1.put("洋芋", "11");
            result1.put("中药", "3");
            result1.put("大豆", "5.2");
            result1.put("玉米", "103");
            result1.put("棉花", "20.4");
        } else if (parms.equals("内江市")) {
            result1.put("水稻", "300");
            result1.put("水果", "1593.06");
            result1.put("茶叶", "1.05");
            result1.put("蔬菜", "80");
            result1.put("洋芋", "12");
            result1.put("大豆", "100");
            result1.put("玉米", "230");
            result1.put("红苔", "275");
        } else if (parms.equals("乐山市")) {
            result1.put("花卉", "0.51");
            result1.put("油菜", "8.6");
            result1.put("水稻", "280");
            result1.put("小麦", "30");
            result1.put("洋芋", "12.16");
            result1.put("玉米", "130");
        } else if (parms.equals("眉山市")) {
            result1.put("花卉", "0.2");
            result1.put("油菜", "0.25");
            result1.put("水稻", "176.7");
            result1.put("水果", "25.5");
            result1.put("茶叶", "0.94");
            result1.put("蔬菜", "9.63");
            result1.put("玉米", "60.6");
            result1.put("花生", "2.41");
        } else if (parms.equals("南充市")) {
            result1.put("小麦", "279");
            result1.put("水果", "78");
            result1.put("洋芋", "3.95");
            result1.put("玉米", "40.5");
            result1.put("棉花", "30.6");
            result1.put("红苔", "41.4");
        } else if (parms.equals("宜宾市")) {
            result1.put("油菜", "12.1");
            result1.put("水果", "75.8");
            result1.put("茶叶", "48.83");
            result1.put("大豆", "3.5");
            result1.put("玉米", "85");
        } else if (parms.equals("广安市")) {
            result1.put("水果", "96");
            result1.put("玉米", "70");
        } else if (parms.equals("达州市")) {
            result1.put("油菜", "50");
            result1.put("水稻", "150");
            result1.put("小麦", "36");
            result1.put("茶叶", "1.1");
            result1.put("洋芋", "12.9");
            result1.put("玉米", "109.95");
            result1.put("竹子", "19.95");
            result1.put("干果", "10.05");
        } else if (parms.equals("雅安市")) {
            result1.put("水稻", "28.9");
            result1.put("小麦", "0.85");
            result1.put("水果", "29.5");
            result1.put("茶叶", "6.07");
            result1.put("大豆", "0.95");
            result1.put("玉米", "30");
        } else if (parms.equals("巴中市")) {
            result1.put("油菜", "10");
            result1.put("小麦", "29.4");
            result1.put("蔬菜", "30");
            result1.put("大豆", "12");
            result1.put("烤烟", "0.15");
            result1.put("桑蚕", "53.93");
        } else if (parms.equals("资阳市")) {
            result1.put("水稻", "150.57");
            result1.put("水果", "34.54");
            result1.put("洋芋", "20");
            result1.put("大豆", "17");
            result1.put("玉米", "47.2");
            result1.put("烤烟", "0.43");
            result1.put("棉花", "49.13");
            result1.put("桑蚕", "5.61");
        } else if (parms.equals("阿坝藏族羌族自治州")) {
            result1.put("水果", "22");
            result1.put("蔬菜", "20");
            result1.put("玉米", "15");
            result1.put("干果", "30");
            result1.put("青稞", "18");
        } else if (parms.equals("甘孜藏族自治州")) {
            result1.put("油菜", "1.94");
            result1.put("小麦", "16.67");
            result1.put("蔬菜", "3.63");
            result1.put("洋芋", "104.7");
            result1.put("玉米", "9.66");
            result1.put("大豆", "15.8");
            result1.put("青稞", "44.85");
        } else if (parms.equals("凉山彝族自治州")) {
            result1.put("花卉", "2.85");
            result1.put("油菜", "8.42");
            result1.put("水稻", "16");
            result1.put("小麦", "100.9");
            result1.put("水果", "67.9");
            result1.put("茶叶", "1.4");
            result1.put("洋芋", "93.2");
            result1.put("玉米", "123.15");
            result1.put("桑蚕", "0.4");
        } else if (parms.equals("攀枝花市")) {
            result1.put("水果", "52.52");
            result1.put("蔬菜", "13.8");
        }
        Iterator iterator = result1.keySet().iterator();
        List<MapTmp> mapTmps = new ArrayList<>();
        while (iterator.hasNext()) {
            String name = iterator.next().toString();
            Double value = Double.parseDouble(result1.get(name).toString());
            MapTmp mapTmp = new MapTmp();
            mapTmp.setName(name);
            mapTmp.setValue(value);
            mapTmps.add(mapTmp);
        }
        plantSes = mapTmps;
        return "manager/home/map-1";
    }

    @RequestMapping("getData")
    @ResponseBody
    public List getData() {
        return plantSes;
    }

    @RequestMapping("getTwo")
    public String goToTwo() {
        return "manager/home/map-2";
    }

    @RequestMapping("goBack")
    public String goBack() {
        return "manager/home/map";
    }
}
