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

public class Test003_Channel {


    
    public static void main(String[] args)
    throws IOException {
       // Test001();//1.利用通道完成文件的copy(非直接存储) 
       //Test002(); //2.使用直接存储完成文件的复制(内存映射)
        Test003();//3.通道和通道之间传输复制文件（直接存储）
    }

    
    //1.利用通道完成文件的copy(非直接存储) 
    public static void Test001() throws IOException {      
        FileInputStream in=new FileInputStream("1.png");  
        FileOutputStream out=new FileOutputStream("2.png");

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

    }



    //2.使用直接存储完成文件的复制(内存映射)
    public static void Test002() throws IOException{

        //获取通道Open()
        FileChannel inChannel=FileChannel.open(Paths.get("1.PNG"), StandardOpenOption.READ);
        FileChannel outChannel=FileChannel.open(Paths.get("3.PNG"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMapBuffer=inChannel.map(MapMode.READ_ONLY,0, inChannel.size());
        MappedByteBuffer outMapBuffer=outChannel.map(MapMode.READ_WRITE,0,inChannel.size());

        //直接对缓冲区进行读写
        byte[] dst=new byte[inMapBuffer.limit()];
        inMapBuffer.get();
        outMapBuffer.put(dst);

        inChannel.close();
        outChannel.close();
        

        

    }
    //3.通道和通道之间传输复制文件（直接存储
    public static void Test003() throws IOException{
        
    //获取通道Open()
    FileChannel inChannel=FileChannel.open(Paths.get("1.PNG"), StandardOpenOption.READ);
    FileChannel outChannel=FileChannel.open(Paths.get("4.PNG"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

    //通道之间获取用TransferFrom or transferto
    //inChannel.transferTo(0, inChannel.size(), outChannel);
    outChannel.transferFrom(inChannel, 0, inChannel.size());
    inChannel.close();
    outChannel.close();

    }

}


        
        
        
        

