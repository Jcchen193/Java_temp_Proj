package com.java.example.demo.service;

import java.util.List;

public interface CreateThreadService {

	List<String> getThreadByThread();
	List<String> getThreadByRunable();
	List<String> getThreadByCallable();
	List<String> getMultipleThreadByThread();
	List<String> getConcurrentMapTest();
	List<String> getCopyOnWriteTest();

}
