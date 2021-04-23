package com.java.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.java.example.demo.service.CreateThreadService;

@Service
public class CreateThreadServiceImpl implements CreateThreadService {

	@Override
	public List<String> getThreadByThread() {
		//1.继承Thread
		List<String> threadList =new ArrayList<String>();
		Thread thread = new Thread() {
		 @Override
		 public void run() {
		 System.out.println("继承Thread");
		 threadList.add("继承Thread");
		 super.run();
		 }
		 };
		 thread.start();
		return threadList;
	}

	@Override
	public List<String> getThreadByRunable() {
		//2.实现runable接口
		List<String> threadList =new ArrayList<String>();
		Thread thread1 = new Thread(new Runnable() {
			 @Override
			 public void run() {
			 System.out.println("实现runable接口");
			 threadList.add("实现runable接口");
		 }
		 });
		 thread1.start();
		 
		return threadList;
	}

	@Override
	public List<String> getThreadByCallable() {
		//3.实现callable接口
		List<String> threadList =new ArrayList<String>();
		ExecutorService service = Executors.newSingleThreadExecutor();
		java.util.concurrent.Future future = service.submit(new Callable() {
		 @Override
		 public String call() throws Exception {
			 threadList.add( "通过实现Callable接口");
			 return "通过实现Callable接口";
		 }
		 });
		 try {
		 Object result = future.get();
		 System.out.println(result);
		 } catch (InterruptedException e) {
		 e.printStackTrace();
		 } catch (ExecutionException e) {
		 e.printStackTrace();
		 }
		 return threadList;
	}

	@Override
	public List<String> getMultipleThreadByThread() {
		//通过实现runnable接口创建多并发的线程
		List<String> threadList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					//System.out.println("我是" + Thread.currentThread().getName() + ".");
					threadList.add("我是" + Thread.currentThread().getName() + ".");
				}
			});
			t.start();
		}
		
		return threadList;

	}
	
}
