package com.java.example.demo.test;

public class Test {

    public static void main(String[] args){
        testStrings("ab", "cd", "ef");
    }

    public static void testStrings(String... keys){
        for(String key : keys){
            System.out.println("this key is: " + key);
        }
    }

}
