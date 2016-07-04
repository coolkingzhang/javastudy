package org.javastudy.hadoop.zookeeper.curator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator framework watch test.
 */

public class CuratorNodeLister {

	/** Zookeeper info */
	private static final String ZK_ADDRESS = "192.168.189.201:2181";
	private static final String ZK_PATH = "/zk-huey";

	public static void main(String[] args) throws Exception {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		/**
		 * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
		 */
		ExecutorService pool = Executors.newFixedThreadPool(2);

		/**
		 * 监听数据节点的变化情况
		 */
		final NodeCache nodeCache = new NodeCache(client, "/zk-huey/cnode", false);
		nodeCache.start(true);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			public void nodeChanged() throws Exception {
				System.out
						.println("Node data is changed, new data: " + new String(nodeCache.getCurrentData().getData()));
			}
		}, pool);
		System.out.println("Register zk watcher successfully!");
		Thread.sleep(Integer.MAX_VALUE);
	}
}
