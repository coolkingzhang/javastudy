package org.javastudy.cache.memcache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.whalin.MemCached.MemCachedClient;

public class TestMemcache {
	MemCachedClient memCachedClient;

	@Before
	public void beforeTest() {

		ApplicationContext atx = new ClassPathXmlApplicationContext("spring-memcache.xml");
		memCachedClient = (MemCachedClient) atx.getBean("memCachedClient");
	}

	@Test
	public void TestMem() {
		memCachedClient.set("key", "kfc");
		System.out.println(memCachedClient.get("key"));
	}

}