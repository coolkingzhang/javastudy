package org.javastudy.cache.redis.jodis;

import java.util.zip.CRC32;

import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;

import redis.clients.jedis.Jedis;

public class JodisTest {

	public static void main(String[] args) {
		JedisResourcePool jedisPool = RoundRobinJedisPool.create().curatorClient("192.168.189.201:2181", 30000)
				.zkProxyDir("/zk/codis/db_test/proxy").build();
		try (Jedis jedis = jedisPool.getResource()) {
			for (int i = 0; i < 50; i++) {
				CRC32 crc32 = new CRC32();

				// System.out.println(crc32.getValue());

				String key = "foofsdfsdfsd" + i;
				crc32.update(key.getBytes());
				jedis.set(key, "bar");
				System.out.println(crc32.getValue() % 1024);
				// jedis.setex( "codis-test", 2000, "codis-test-value" );
				// String value = jedis.get( "foo" );
				// System.out.println( value );
			}
		}
	}
}
