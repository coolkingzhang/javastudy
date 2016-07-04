package org.javastudy.hadoop.zookeeper.curator;

import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 使用Curator创建节点
 * 
 * @author huey
 * @version 1.0 
 * @created 2015-3-1
 */
public class CuratorCreate {

	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.189.201:2181")
				.sessionTimeoutMs(1000).connectionTimeoutMs(1000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath("/zk");
		
		// 创建节点
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zk-huey/test100",
				"hello world 100 ".getBytes());
		
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zk-huey/test200",
				"hello world 200".getBytes());
		
		// 更新节点
		client.setData().withVersion(-1).forPath("/zk-huey/cnode", "aaaaa".getBytes());

		// 读取节点
		Stat stat = new Stat();
		byte[] nodeData = client.getData().storingStatIn(stat).forPath("/zk-huey/test100");
		System.out.println("NodeData: " + new String(nodeData));
		System.out.println("Stat: " + stat);

		// 获取子节点
		List<String> children = client.getChildren().forPath("/zk-huey");
		System.out.println("Children: " + children);

		// 删除节点
//		client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath("/zk-huey");
		
		client.close();
	}
}