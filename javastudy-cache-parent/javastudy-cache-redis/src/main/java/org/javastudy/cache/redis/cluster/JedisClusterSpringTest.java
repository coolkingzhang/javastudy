package org.javastudy.cache.redis.cluster;

import java.util.List;
import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Transaction;

/**
 * jedisCluster 测试例子
 * 
 * http://my.oschina.net/zhuguowei/blog/411077
 * 
 * @author zhang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-cluster-redis.xml" })
public class JedisClusterSpringTest {

	@Autowired
	JedisCluster jedisCluster;

	// @Ignore
	@Test
	public void testClusterSet() {
		jedisCluster.set("jtest", "This is a jtest for set");
		String value = jedisCluster.get("jtest");
		jedisCluster.set("abc", "This is a jtest for set");
		String value1 = jedisCluster.get("abc");
		jedisCluster.set("sstt", "This is a jtest for set");
		String value2 = jedisCluster.get("xxx");
		jedisCluster.set("fdf", "This is a jtest for set");
		String value3 = jedisCluster.get("fdfd");
		System.out.println(value3);
	}

	// @Ignore
	@Test
	public void testClusterSetn() {
		jedisCluster.setex("myset", 3600, "this is a test for setn");
		String value = jedisCluster.get("myset");
		System.out.println(value);
	}

	// @Ignore
	@Test
	public void testClusterHash() {
		jedisCluster.hset("jhash", "user", "this is a test hash");
		String value = jedisCluster.hget("jhash", "user");
		System.out.println(value);
	}

	@Test
	public void testIncr() {
		String key = "page_view";
		jedisCluster.del(key);
		jedisCluster.incr(key);
		jedisCluster.incr(key);
		jedisCluster.incr(key);
		String result = jedisCluster.get(key);
		System.out.println(result);

	}

	// @Test
	public void test_setAndGetStringVal() {
		String key = "foo";
		String value = "bar";
		jedisCluster.set(key, value);
		String result = jedisCluster.get(key);
		System.out.println(result);
	}

	// @Test
	public void test_setAndGetStringVal_and_set_expire() throws InterruptedException {
		String key = "hello";
		String value = "world";
		int seconds = 3;
		jedisCluster.setex(key, seconds, value);
		String result = jedisCluster.get(key);
		System.out.println(result);
		Thread.sleep(seconds * 1000);
		result = jedisCluster.get(key);
		System.out.println(result);
	}

	@Test
	public void test_setAndGetHashVal() {
		String key = "website";
		String field = "google";
		String value = "google.com";
		jedisCluster.del(key);
		jedisCluster.hset(key, field, value);
		String result = jedisCluster.hget(key, field);
		System.out.println(result);
	}

	@Test
	public void test_setAndGetListVal() {
		String key = "mylist";
		jedisCluster.del(key);
		String[] vals = { "a", "b", "c" };
		jedisCluster.rpush(key, vals);
		List<String> result = jedisCluster.lrange(key, 0, -1);
		System.out.println(result);
	}

	@Test
	public void test_setAndGetSetVal() {
		String key = "language";
		jedisCluster.del(key);
		String[] members = { "java", "ruby", "python" };
		jedisCluster.sadd(key, members);
		Set<String> result = jedisCluster.smembers(key);
		System.out.println(result);
	}
}
