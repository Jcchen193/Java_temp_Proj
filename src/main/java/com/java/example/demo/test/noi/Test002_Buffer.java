package com.java.example.demo.test.noi;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

//创建一个ByteBuffer字节缓冲区
public class Test002_Buffer {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        CharBuffer Charbuffer=buffer.asCharBuffer();//字节顺序，Byte：1；Char：2
        buffer.put(0,(byte)0);
        buffer.put(1,(byte)'H');
        buffer.put(2,(byte)0);
        buffer.put(3,(byte)'i');
        buffer.put(4,(byte)0);
        buffer.put(5,(byte)'!');
        buffer.put(6,(byte)0);
        
        /*Put和Get分为绝对和相对属性
        绝对存取：带索引的，数据存取不影响Position()的位置*/
        System.out.println(buffer.position());
        Test(buffer);
        Test(Charbuffer); // 1024/2字节数=512
    }
    
        public static void Test(Buffer B){
               System.out.println("Pos="+B.position()+" limit= "+B.limit()+
        " Capacity= "+B.capacity()+" ToString= "+B.toString());
        }
        
       
    }

