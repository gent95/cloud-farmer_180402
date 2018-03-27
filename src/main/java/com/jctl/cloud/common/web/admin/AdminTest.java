package com.jctl.cloud.common.web.admin;

import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

@Controller
public class AdminTest {

    @Autowired
    private RelayService relayService;


    @RequestMapping("adminTest")
    public String test() {
        List<Relay> relays = relayService.getTest();
        System.out.print(relays);
        return "forward:index.html";
    }

    @RequestMapping("download")
    public String download(HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream stream = new FileInputStream(new File("C:111.png"));
            response.setContentType("application/octet-stream; charset=utf-8;filename=123.jpg");
            OutputStream outputStream;

//            outputStream.;
            int len = 0;
            byte[] buffer = new byte[1024];
            outputStream = response.getOutputStream();
            while ((len = stream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "test";
    }

    @RequestMapping("redis")
    public void redis() {
        try {
            List<Relay> relays =relayService.findList(new Relay());
            System.out.print(relays);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
