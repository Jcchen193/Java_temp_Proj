package com.java.example.demo.test.noi;


import java.nio.CharBuffer;

//填充或释放数据
public class Test001_Buffer {

    
     public static void main(String[] args) {
      CharBuffer buffer=CharBuffer.allocate(1024);

      while(PutBuffer(buffer)){
      buffer.flip();//切换读写模式，从写模式切换到读模式
      GetBuffer(buffer);
      buffer.clear();
      }
}

public static String[] strings={
    "Test001",
    "Test002",
    "Test003",
};
public static int index = 0;


//Buffer.put()
public static boolean PutBuffer(CharBuffer buffer)
{
    if(index>=strings.length){
        return false;
    }

    //填充数据
    String s=strings[index];
    index++;
    for(int i=0;i<s.length();i++){
        buffer.put(s.charAt(i));
    }

    return true;
}

public static void GetBuffer(CharBuffer buffer){
    while(buffer.hasRemaining()){
        System.out.print(buffer.get());
    }
    System.out.println();
}

}