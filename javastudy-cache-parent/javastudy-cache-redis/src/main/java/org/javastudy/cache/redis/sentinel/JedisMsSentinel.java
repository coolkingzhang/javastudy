package org.javastudy.cache.redis.sentinel;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;
import com.penglecode.common.redis.jedis.ms.ShardedMasterSlaveJedis;
import com.penglecode.common.redis.jedis.ms.ShardedMasterSlaveJedisSentinelPool;


//http://itindex.net/detail/52975-jedis-redis-sentinel 
//Redis分片加主从哨兵机制

public class JedisMsSentinel {

	public static void main( String[] args ) {
		Set<String> masterNames = new LinkedHashSet<String>();
		masterNames.add("mymaster");
//		masterNames.add("master-2");
		Set<String> sentinels = new LinkedHashSet<String>();
		sentinels.add("192.168.189.201:26379");
		sentinels.add("192.168.189.201:36379");
		JedisPoolConfig config = new JedisPoolConfig();
		Pool<ShardedMasterSlaveJedis> shardedMasterSlaveJedisPool = new ShardedMasterSlaveJedisSentinelPool(masterNames, sentinels,config);

		ShardedMasterSlaveJedis shardedMasterSlaveJedis = shardedMasterSlaveJedisPool.getResource();
		for( int i = 0; i < 500; i++ ) { 
		    String key = "shard-" + i;
		    
		    shardedMasterSlaveJedis.set(key, String.valueOf(i));
		    // sharded in master-1[192.168.137.101:6379] for keys : shard-0, shard-2, shard-6, shard-8, shard-9, and sharded in master-2[192.168.137.101:6382] for keys : shard-1, shard-3, shard-4, shard-5, shard-7
		    System.out.println(key + " = " + shardedMasterSlaveJedis.get(key));
//		    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
		    System.out.println(key + " = " + shardedMasterSlaveJedis.getShard(key).opsForSlave().get(key)); // Get from one master group's one slave
		}
		shardedMasterSlaveJedisPool.returnResource(shardedMasterSlaveJedis);
		shardedMasterSlaveJedisPool.close();
	}
}
