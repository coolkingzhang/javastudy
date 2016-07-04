package org.javastudy.cache.memcache;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.whalin.MemCached.MemCachedClient;

// http://blog.csdn.net/ycyk_168/article/details/11294263
public class TestMemcache {
	MemCachedClient memCachedClient;

	@Before
	public void beforeTest() {

		ApplicationContext atx = new ClassPathXmlApplicationContext("spring-memcache.xml");
		memCachedClient = (MemCachedClient) atx.getBean("memCachedClient");
	}

	@Test
	public void set() {
		memCachedClient.set("key", "This is a Key for memcache");
		System.out.println(memCachedClient.get("key"));
		
	}

	@Test
	public void setx() {
		/**
		 * 10秒生存时间
		 * 
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		System.out.println("当前时间：" + sdf.format(now));
		Date afterDate = new Date(now.getTime() + 10000);
		System.out.println(sdf.format(afterDate));

//		memCachedClient.set("mykey", "this is value", afterDate);
		System.out.println(memCachedClient.get("mykey"));
	}
}