package com.jctl.cloud.manager.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gent on 2017/4/19.
 */
@Controller
@RequestMapping("manager")
public class ChatController {
    @RequestMapping(value = "chat")
    public String toChat(){
        return "manager/chat/chatRoom";
    }
}
