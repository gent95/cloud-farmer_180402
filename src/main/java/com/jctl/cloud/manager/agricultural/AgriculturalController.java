package com.jctl.cloud.manager.agricultural;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gent on 2017/4/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/agricultural")
public class AgriculturalController {
    @RequestMapping("/show")
    public String show(){
            return "manager/agricultural/agricultural";
        }
}
