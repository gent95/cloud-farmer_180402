package com.jctl.cloud.utils;

import java.io.*;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/22.
 */
public class HtmlUtil {

    public static void writeToHtml() throws Exception{
        File dest = new File("C:/index.html");
        InputStream is;//接收字节输入流
        FileOutputStream fos = new FileOutputStream(dest);//字节输出流

        URL wangyi = new URL("http://60.255.50.139");
        is = wangyi.openStream();

        BufferedInputStream bis = new BufferedInputStream(is);//为字节输入流加缓冲
        BufferedOutputStream bos = new BufferedOutputStream(fos);//为字节输出流加缓冲

        int length;

        byte[] bytes = new byte[1024*20];
        while((length = bis.read(bytes, 0, bytes.length)) != -1){
            fos.write(bytes, 0, length);
        }

        bos.close();
        fos.close();
        bis.close();
        is.close();
    }
    public static void main(String [] args){
        try {
//            way_2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
