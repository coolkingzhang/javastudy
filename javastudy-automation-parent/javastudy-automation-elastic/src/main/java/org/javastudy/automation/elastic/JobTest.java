package org.javastudy.automation.elastic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:job.xml");
		
	}
}
