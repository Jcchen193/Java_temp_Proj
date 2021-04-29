package com.java.example.demo.test.noi;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

import org.junit.Test;

/**
 * 
 * Pipe有两个通道sink通道和Source通道。
 * 数据会被写到sink中，从Source通道读取。
 *
 */
public class Test005_Pipe {
	
	@Test
	public void Pipe() throws IOException {
	
		//1.获取管道
		Pipe pipe=Pipe.open();
		
		//2.分配缓冲区
		ByteBuffer buf=ByteBuffer.allocate(1024);
		
		//3.通过SinkChannel的Write()写入数据
		Pipe.SinkChannel sinkChannel=pipe.sink();
		buf.put("通道单向管道发送数据".getBytes());
		buf.flip();
		sinkChannel.write(buf);
		
		//4.读取缓冲区的数据
		Pipe.SourceChannel sourceChannel=pipe.source();
		buf.flip();
		int len=sourceChannel.read(buf);
		System.out.println(new String(buf.array(),0,len));
		
		//5.关闭通道
		sinkChannel.close();
		sourceChannel.close();
		
		
		
		
		
		
		
		
	}
}
