package com.java.example.demo.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.junit.Test;

public class TestNonBlockingNioServer {
	@Test
    public void server() throws IOException{
        //1.获取通道
        ServerSocketChannel sschannel=ServerSocketChannel.open();

        //2.2.切换非阻塞模式
        sschannel.configureBlocking(false);

        //3.连接通道
        sschannel.bind(new InetSocketAddress(9898));
        //4.创建选择器
        Selector selector=Selector.open();

        //5.把通道注册到选择器上，并指定“接收监听事件”，SelectionKey有4种监听
        sschannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮询式的获取选择器上已经"准备就绪"的事件
        while(selector.select()>0){

            //7.selectedKeys()：获取当前选择器所有注册“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> it=selector.selectedKeys().iterator();
            while(it.hasNext()){

                //8.获取准备“就绪事件”
                SelectionKey sk=it.next();

                //9.判断具体是什么事件“准备就绪”，也有4个方法
                if(sk.isAcceptable()){

                    //10.若"接收就绪"，获取客户端连接
                    SocketChannel sChannel=sschannel.accept();

                    //11.切换非阻塞模式
                    sChannel.configureBlocking(false);

                    //12.将通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);


                }else if(sk.isReadable()){

                    //13.获取当前选择器上“读就绪”的通道
                    SocketChannel sChannel=(SocketChannel)sk.channel();
                    
                    //14.读取数据
                    ByteBuffer buf=ByteBuffer.allocate(1024);

                    int len=0;
                    while((len=sChannel.read(buf))>0){
                        buf.flip();
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }


                }

                //15.取消选择键SelectionKey
                it.remove();

                    
                
            }
        }


	}
}
