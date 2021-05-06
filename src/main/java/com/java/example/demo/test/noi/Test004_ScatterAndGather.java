package com.java.example.demo.test.noi;



import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/*Scatter and Gather
 *  分散读取(Scattering Reads):将通道中的数据分散到多个缓冲区
 *  聚集写入(Gathering Write):将多个缓冲区的数据聚集到通道
 */
public class Test004_ScatterAndGather {

	@Test
	public void ScatterAndGather() throws IOException {
		RandomAccessFile fos=new RandomAccessFile("src/main/resources/static/images/1.txt","rw");
		
		//1.获取通道
		FileChannel sChannel=fos.getChannel();
		
		//2.分配缓冲区
		ByteBuffer buf1=ByteBuffer.allocate(100);
		ByteBuffer buf2=ByteBuffer.allocate(1024);
		ByteBuffer buf3=ByteBuffer.allocate(1024);
		
		
		//3.分散读取	:Scatter
		ByteBuffer[] bufs={buf1,buf2,buf3};
		sChannel.read(bufs);
		for(ByteBuffer byteBuffer:bufs) {
			byteBuffer.flip();
		}
		
		System.out.println("第一个缓冲区的数据：");
		System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
		System.out.println("第二个缓冲区的数据：");
		System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));
		System.out.println("第三个缓冲区的数据：");
		System.out.println(new String(bufs[2].array(),0,bufs[2].limit()));
		
		//聚集写入：Gather	
		RandomAccessFile fos1=new RandomAccessFile("src/main/resources/static/images/2.txt","rw");
		FileChannel ssChannel=fos1.getChannel();
		ssChannel.write(bufs);
		
		//关闭通道
		sChannel.close();
		ssChannel.close();
		fos1.close();
		fos.close();
		

		
		
		
	}
	
}
