package com.java.example.demo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTest {
	
	@Scheduled(cron = "0/5 * * * * *")
	public void lock() {
		//System.out.println("test scheduled case!");
	}
	
}
