package com.jctl.cloud.mina.server;

import com.jctl.cloud.mina.handler.MinaUdpLongConnServerHandler;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by  on 2017/5/15.
 */
public class MinaUdpLongConnServer {

    private static final long serialVersionUID = 1L;

    private static final int PORT = 41567;

    public void start() {

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();//创建一个UDP的接收器
        acceptor.setHandler(new MinaUdpLongConnServerHandler());//设置接收器的处理程序

        Executor threadPool = Executors.newFixedThreadPool(1500);//建立线程池
        acceptor.getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());

        DatagramSessionConfig dcfg = acceptor.getSessionConfig();//建立连接的配置文件

        dcfg.setReadBufferSize(4096);//设置接收最大字节默认2048
        dcfg.setReceiveBufferSize(1024);//设置输入缓冲区的大小
        dcfg.setSendBufferSize(1024);//设置输出缓冲区的大小
        dcfg.setReuseAddress(true);//设置每一个非主监听连接的端口可以重用

        try {
            acceptor.bind(new InetSocketAddress(PORT));//绑定端口
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {


        new MinaUdpLongConnServer().start();
    }


}
