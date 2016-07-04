package org.javastudy.cache.j2cache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.J2Cache;

/**
 * Hello world!
 *
 */
public class J2cacheTest {
	public static void main(String[] args) {
		CacheChannel cache = J2Cache.getChannel();
//		cache.set("cache1", "key", "OSChina.net");
		CacheObject obj = cache.get("cache1", "key");
		System.out.println(obj.getRegion() + obj.getValue());
		// cache.evict("cache1","key500");
		// cache.close();
	}
}
