package org.javastudy.hadoop.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用Curator创建会话
 * 
 * @author huey
 * @version 1.0
 * @created 2015-3-1 
 */
public class CuratorDemo {

	public static void main(String[] args) throws Exception {
		CuratorTest();
	}

	public static void CuratorMyTest() {
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", // 服务器列表
				5000, // 会话超时时间，单位毫秒
				3000, // 连接创建超时时间，单位毫秒
				new ExponentialBackoffRetry(1000, 3) // 重试策略
		);
		client.start();
		client.close();
	}

	public static void CuratorTest() {
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000).connectionTimeoutMs(3000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		client.close();
	}
}