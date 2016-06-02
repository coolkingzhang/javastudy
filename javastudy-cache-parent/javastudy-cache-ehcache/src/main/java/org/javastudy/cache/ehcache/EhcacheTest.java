package org.javastudy.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {
	/**
	 * http://www.cnblogs.com/yjmyzz/p/3908570.html 参考例子
	 * 
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		CacheManager manager = CacheManager.create();

		// 取出所有的cacheName
		String names[] = manager.getCacheNames();
		System.out.println("----all cache names----");
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}

		System.out.println("----------------------");
		// 得到一个cache对象
		Cache cache1 = manager.getCache(names[0]);

		// 向cache1对象里添加缓存
		cache1.put(new Element("key1", "values1"));
		Element element = cache1.get("key1");

		// 读取缓存
		System.out.println("key1 \t= " + element.getObjectValue());

		// 手动创建一个cache(ehcache里必须有defaultCache存在,"test"可以换成任何值)
		Cache cache2 = new Cache("test", 1, true, false, 2, 3);
		manager.addCache(cache2);

		cache2.put(new Element("jimmy", "菩提树下的杨过"));

		// 故意停1.5秒，以验证是否过期
		Thread.sleep(1500);

		Element eleJimmy = cache2.get("jimmy");

		// 1.5s < 2s 不会过期
		if (eleJimmy != null) {
			System.out.println("jimmy \t= " + eleJimmy.getObjectValue());
		}

		// 再等上0.5s, 总时长：1.5 + 0.5 >= min(2,3),过期
		Thread.sleep(500);

		eleJimmy = cache2.get("jimmy");

		if (eleJimmy != null) {
			System.out.println("jimmy \t= " + eleJimmy.getObjectValue());
		}

		// 取出一个不存在的缓存项
		System.out.println("fake \t= " + cache2.get("fake"));

		manager.shutdown();
	}

}