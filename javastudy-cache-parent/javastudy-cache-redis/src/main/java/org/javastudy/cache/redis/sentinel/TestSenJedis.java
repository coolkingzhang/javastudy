package org.javastudy.cache.redis.sentinel;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSenJedis {

	public static void main( String[] args ) {
		try {
			testJedis();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}

	public static void testJedis() throws InterruptedException {

		Set<String> sentinels = new HashSet<String>();
		sentinels.add( "192.168.189.201:26379" );
		JedisSentinelPool sentinelPool = new JedisSentinelPool( "mymaster",
				sentinels );
		Jedis jedis = sentinelPool.getResource();
		System.out.println( "current Host:"
				+ sentinelPool.getCurrentHostMaster() );

		String key = "show";
		String cacheData = jedis.get( key );
		if( cacheData == null ) {
			jedis.del( key );
		}
		jedis.set( key, "aaaaffa" );// 写入
		System.out.println( jedis.get( key ) );// 读取
		System.out.println( "current Host:"
				+ sentinelPool.getCurrentHostMaster() );// down掉master，观察slave是否被提升为master
		jedis.set( key, "bbb" );// 测试新master的写入
		System.out.println( jedis.get( key ) );// 观察读取是否正常
		sentinelPool.close();
		jedis.close();
	}
}
