package org.javastudy.cache.redis.cluster;

import java.util.Set;
import java.util.HashSet;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * http://www.myexception.cn/industry/1919001.html
 * 
 * @author zhang
 *
 */
public class JedisClusterTest {
	public static void main(String[] args) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

		jedisClusterNodes.add(new HostAndPort("10.73.128.85", 7001));
		jedisClusterNodes.add(new HostAndPort("10.73.128.86", 7001));
		jedisClusterNodes.add(new HostAndPort("10.73.128.87", 7001));
		// jedisClusterNodes.add(new HostAndPort("10.73.128.85", 7000));
		// jedisClusterNodes.add(new HostAndPort("10.73.128.86", 7000));
		// jedisClusterNodes.add(new HostAndPort("10.73.128.87", 7000));

		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
		jedisCluster.set("page_view", "This is a fdsd");
		String value = jedisCluster.get("page_view");
		System.out.println(value);

		jedisCluster.set("cc", "This is a cc");
		String value1 = jedisCluster.get("cc");
		System.out.println(value1);

		jedisCluster.set("jtest", "This is a jtest");
		String value2 = jedisCluster.get("jtest");
		System.out.println(value2);

		jedisCluster.set("ss", "This is a ss");
		String value3 = jedisCluster.get("ss");
		System.out.println(value3);

		jedisCluster.set("aaeee", "This is a ss");
		String value4 = jedisCluster.get("aaeee");
		System.out.println(value4);

		// System.out.println(jedisCluster.get("jtest"));
	}
}
