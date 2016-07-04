package org.javastudy.cache.memcache;

import java.io.IOException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class Xcache {
	public static void main(String[] args) throws IOException {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("127.0.0.1:11211"));
		MemcachedClient memcachedClient = builder.build();
		try {
			// memcachedClient.set("hello88", 0, "Hello,xmemcached");
			// String value = memcachedClient.get("lock");
			// System.out.println("add lock:" + value);
			//
			// System.out.println("########################################################");
			// System.out.println(memcachedClient.add("lock", 0, "lock9"));
			// memcachedClient.delete("lock");
			//
			// System.out.println(memcachedClient.add("lock", 0, "lock9"));
			// memcachedClient.delete("lock");
			//
			// System.out.println("########################################################");

			memcachedClient.delete("lock");

			if (memcachedClient.add("lock", 0, "lock9")) {
				System.out.println("开锁成功");
			} else {
				System.out.println("开锁失败");
			}

			if (memcachedClient.add("lock", 0, "lock9")) {
				System.out.println("开锁成功");
			} else {
				System.out.println("开锁失败");
			}

			System.out.println(memcachedClient.add("bb", 0, "value", 30));
			System.out.println(memcachedClient.add("bb", 0, "value", 30));
			
			System.out.println(memcachedClient.add("bb", 0, "value", 30));
			System.out.println(memcachedClient.add("bb", 0, "value", 30));
			//////////////////////////////////////////////////////////////////

			memcachedClient.set("mymemcached", 0, "Hello,xmemcached");
			System.out.println(memcachedClient.get("mymemcached"));

			memcachedClient.set("mymemcached", 0, "Hello,xmemcached");
			System.out.println(memcachedClient.get("mymemcached"));

		} catch (Exception e) {
		}
		try {
			memcachedClient.shutdown();
		} catch (IOException e) {
			System.err.println("Shutdown MemcachedClient fail");
			e.printStackTrace();
		}
	}
}