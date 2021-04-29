package com.java.example.demo.test.noi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class Test003_Channel {


    @Test
    //1.利用通道完成文件的copy(非直接存储) 
    public void Test001() throws IOException { 
    	long start=System.currentTimeMillis();
    	
        FileInputStream in=new FileInputStream("src/main/resources/static/images/1.png");  
        FileOutputStream out=new FileOutputStream("src/main/resources/static/images/2.png");

        //获取通道
        FileChannel inChannel=in.getChannel();
        FileChannel outChannel=out.getChannel();

        //建立缓冲区
        ByteBuffer Buffer=ByteBuffer.allocate(1024);

        while(inChannel.read(Buffer)!=-1){
        Buffer.flip();
        outChannel.write(Buffer);
        Buffer.clear();
        }
        inChannel.close();
        outChannel.close();
        in.close();
        out.close();
        
        long end=System.currentTimeMillis();
        System.out.print("消耗时间001:"+(end-start));

    }


    @Test
    //2.使用直接存储完成文件的复制(内存映射)
    public void Test002() throws IOException{

    	long start=System.currentTimeMillis();
        //获取通道Open()
        FileChannel inChannel=FileChannel.open(Paths.get("src/main/resources/static/images/1.PNG"), StandardOpenOption.READ);
        FileChannel outChannel=FileChannel.open(Paths.get("src/main/resources/static/images/3.PNG"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMapBuffer=inChannel.map(MapMode.READ_ONLY,0, inChannel.size());
        MappedByteBuffer outMapBuffer=outChannel.map(MapMode.READ_WRITE,0,inChannel.size());

        //直接对缓冲区进行读写
        byte[] dst=new byte[inMapBuffer.limit()];
        inMapBuffer.get();
        outMapBuffer.put(dst);

        inChannel.close();
        outChannel.close();
        
        long end=System.currentTimeMillis();
        System.out.print("消耗时间002:"+(end-start));
        

    }
    
    
    
    @Test
    //3.通道和通道之间传输复制文件（直接存储
    public void Test003() throws IOException{
        
    long start=System.currentTimeMillis();

    //获取通道Open()
    FileChannel inChannel=FileChannel.open(Paths.get("src/main/resources/static/images/1.PNG"), StandardOpenOption.READ);
    FileChannel outChannel=FileChannel.open(Paths.get("src/main/resources/static/images/4.PNG"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

    //通道之间获取用TransferFrom or transferto
    //inChannel.transferTo(0, inChannel.size(), outChannel);
    outChannel.transferFrom(inChannel, 0, inChannel.size());
    inChannel.close();
    outChannel.close();
    long end=System.currentTimeMillis();
    System.out.print("消耗时间003:"+(end-start));

    }

}


        
        
        
        

