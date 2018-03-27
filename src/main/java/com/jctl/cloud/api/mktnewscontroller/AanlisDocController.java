package com.jctl.cloud.api.mktnewscontroller;

import com.jctl.cloud.manager.marketnews.entity.MktDyn;
import com.jctl.cloud.manager.marketnews.service.AnalylsisDocumentImp;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 爬去网络数据作为市场动态
 * @author gent 2017年4月25日下午4:24:09
 */
@Controller
@RequestMapping("aAnlisDocController")
public class AanlisDocController {
    @Autowired
    private AnalylsisDocumentImp analylsisDocumentImp;

    @RequestMapping("getMktDyns")
    @ResponseBody
    public Map<String, Object> getMktDyns(String areaId, HttpServletRequest request) {
        Map<String,Object> result = new HashedMap();
        try{
            List<MktDyn> mktDyns = analylsisDocumentImp.analysisMktDyn(areaId,request);

            if (mktDyns!=null && Integer.parseInt(areaId) >0 && Integer.parseInt(areaId) <8){
                result.put("flag",1);
                result.put("mktDyns",mktDyns);
            }else{
                result.put("flag",0);
                result.put("mktDyns",null);
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","操作失败！");
            e.printStackTrace();
        }

        return result;
    }
}
