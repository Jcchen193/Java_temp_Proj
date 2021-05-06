package com.java.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
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
		
		List<String> threadList = new ArrayList<String>();
		//1.通过实现runnable接口并将其作为Thread对象的参数创建并发
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("我是通过过实现runnable接口并将其作为Thread对象的参数创建并发" + Thread.currentThread().getName() + ".");
					threadList.add("我是通过过实现runnable接口并将其作为Thread对象的参数创建并发" + Thread.currentThread().getName() + ".");
				}
			});
			t.start();
			//t.interrupted();
			//t.stop();
		}
		
		
		//System.out.println("------------------------------");
		
		//2.创建线程池创建并发
//		ExecutorService executorService = Executors.newCachedThreadPool();//创建一个可缓存的线程池
//		// Executors.newFiexedThreadPool(int num); // 创建固定数目线程的线程池
//		//Executors.newSingleThreadExecutor(); //创建单线程的线程池
//
//		for (int i = 0; i < 10; i++) {
//			Runnable r = new Runnable() {
//
//				@Override
//				public void run() {
//					System.out.println("我是创建线程池创建并发" + Thread.currentThread().getName() + ".");
//					threadList.add("我是创建线程池创建并发" + Thread.currentThread().getName() + ".");
//				}
//			};
//			executorService.execute(r);//调用该方法可以重用之前的线程
//		}
//		executorService.shutdown();
		
		//System.out.println("------------------------------");
		//3.直接调用ThreadPoolExecutor的构造函数来创建线程池
//		ExecutorService executor = new ThreadPoolExecutor(10, 10,
//		        60L, TimeUnit.SECONDS,
//		        new ArrayBlockingQueue(10));
//		for (int i = 0; i < 10; i++) {
//			Runnable r = new Runnable() {
//				@Override
//				public void run() {
//					System.out.println("我是调用ThreadPoolExecutor的构造函数创建线程池" + Thread.currentThread().getName() + ".");
//					threadList.add("我是调用ThreadPoolExecutor的构造函数创建线程池" + Thread.currentThread().getName() + ".");
//				}
//			};
//			executor.execute(r);
//		}
//		executor.shutdown();
		
		return threadList;

	}

	@Override
	public List<String> getConcurrentMapTest() {
		
		List<String> threadList = new ArrayList<String>();
		
		ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();

		map.put("k1", "v1");

		map.put("k2", "v2");

		map.put("k1", "v2");

		map.putIfAbsent("k1", "vv1");

		for(Map.Entry me : map.entrySet()) {
			System.out.println("ConcurrentMap- key: " + me.getKey() + ",value: " + me.getValue());
			threadList.add("ConcurrentMap -key: " + me.getKey() + ",value: " + me.getValue());
		}
		
		return threadList;

		}

	@Override
	public List<String> getCopyOnWriteTest() {
		List<String> threadList = new ArrayList<String>();
		CopyOnWriteArrayList copylist = new CopyOnWriteArrayList<>();

		copylist.add("1");

		copylist.add("2");

		for(int i=0;i<copylist.size();i++) {
			System.out.println(copylist.get(i));
			threadList.add("copyOnWrite:" + copylist.get(i));
			
		}

		
				
		return threadList;
	}
	
}
