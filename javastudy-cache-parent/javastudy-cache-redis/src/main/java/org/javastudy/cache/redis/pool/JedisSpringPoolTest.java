package org.javastudy.cache.redis.pool;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * jedisCluster 测试例子
 * 
 * http://my.oschina.net/zhuguowei/blog/411077
 * 
 * @author zhang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-redis.xml" })
public class JedisSpringPoolTest {

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
    protected RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

//	@Test
	public void testShardedJedis() {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.set("aa", "This is web site");
		System.out.println(shardedJedis.get("aa"));
	}

	@Test
	public void testJedis() {
		Jedis jedis = jedisPool.getResource();
		jedis.set("cc", "cc");
		System.out.println(jedis.get("cc"));
	}
	
	@Test
	public void testData(){
		stringRedisTemplate.opsForValue().set("mykey", "mykey spring data redis");
		System.out.println(stringRedisTemplate.opsForValue().get("mykey"));
	}
}
