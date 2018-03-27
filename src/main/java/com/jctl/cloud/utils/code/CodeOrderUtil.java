package com.jctl.cloud.utils.code;

/**
 * Created by Administrator on 2017/4/20.
 */
public class CodeOrderUtil {



    private final Integer H=01;//前置域  地址域

    private final Integer R=01;//只读域 读(0x03)/写(0x10)操作码
    private final Integer RW=03;


    public CodeOrderUtil() {
    }









    /**
     * 十六转十
     * @param num
     * @return
     */
    public static Integer hexToInt(String num) {
        return Integer.parseInt(num, 0x10);
    }



    public static void main(String[] args){
//        hexToInt("FF");
       System.out.println(hexToInt("18"));
    }

}
