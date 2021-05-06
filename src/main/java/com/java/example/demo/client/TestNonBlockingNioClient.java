package com.java.example.demo.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNioClient {
	@Test
    public void client() throws IOException{
        //1.获取通道
        SocketChannel schannel=SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2.切换非阻塞模式
        schannel.configureBlocking(false);

        //3.分配指定大小的缓冲区
        ByteBuffer buf=ByteBuffer.allocate(1024);

        //4.发送数据给服务端
       Scanner scan=new Scanner(System.in);
       while(scan.hasNext()){
        	 String str=scan.next();
        	 buf.put((new Date().toString()+"\n"+str).getBytes());
             buf.flip();
             schannel.write(buf);
             buf.clear();
       }
             
       //5.关闭通道
       schannel.close();
       		/*
             buf.put((new Date().toString().getBytes()));
             buf.flip();
             schannel.write(buf);
             buf.clear();
              */
       
        

	}
}
