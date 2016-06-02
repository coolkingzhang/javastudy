package org.javastudy.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheClient {

	public static void main(String[] args) {

		CacheManager.create();
		String[] cacheNames = CacheManager.getInstance().getCacheNames();
		System.out.println(cacheNames.length);

		CacheManager manager2 = CacheManager.newInstance("src/main/resources/ehcache.xml");
		String[] cacheNames2 = manager2.getCacheNames();
		System.out.println(cacheNames2.length);

		CacheManager singletonManager = CacheManager.create();
		Cache memoryOnlyCache = new Cache("testCache", 5000, false, false, 5, 2);
		singletonManager.addCache(memoryOnlyCache);
		Cache cache = singletonManager.getCache("testCache");

		Element element = new Element("key1", "value1222");
		cache.put(element);
		Element value = cache.get("key1");

		System.out.println(value.getObjectValue());
		// value1
		System.out.println(value.toString());
		// [ key = key1, value=value1, version=1, hitCount=1, CreationTime =
		// 1359130974484, LastAccessTime = 1359130974500 ]
	}
}
