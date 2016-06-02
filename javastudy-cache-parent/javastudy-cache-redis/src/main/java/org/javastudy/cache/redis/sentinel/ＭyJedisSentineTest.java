package org.javastudy.cache.redis.sentinel;

import java.util.HashSet;
import java.util.Set;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class ＭyJedisSentineTest {

	public static void main( String[] args ) {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add( new HostAndPort( "192.168.189.201", 26379 ).toString() );
		JedisSentinelPool sentinelPool = new JedisSentinelPool( "mymaster", sentinels );
		System.out.println( "Current master: " + sentinelPool.getCurrentHostMaster().toString() );
		
		
		Jedis master = sentinelPool.getResource();
		master.set( "username", "liangzhichao" );

		sentinelPool.getResource();
		Jedis master2 = sentinelPool.getResource();
		String value = master2.get( "key" );
		System.out.println( "username: " + value );
		master2.close();
		sentinelPool.destroy();
	}
}
