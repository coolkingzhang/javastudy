package org.javastudy.hadoop.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator framework watch test.
 * 
 */

public class MyLister {

	/** Zookeeper info */
	private static final String ZK_ADDRESS = "192.168.189.201:2181";
	private static final String ZK_PATH = "/zk-huey";

	public static void main(String[] args) throws Exception {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		// 2.Register watcher
		PathChildrenCache watcher = new PathChildrenCache(client, ZK_PATH, true);
		watcher.getListenable().addListener(new PathChildrenCacheListener() {
			public void childEvent(CuratorFramework client1, PathChildrenCacheEvent event) throws Exception {
				ChildData data = event.getData();
				if (data == null) {
					System.out.println("No data in event[" + event + "]");
				} else {
					System.out.println("Receive event: " + "type=[" + event.getType() + "]" + ", path=["
							+ data.getPath() + "]" + ", data=[" + new String(data.getData()) + "]" + ", stat=["
							+ data.getStat() + "]");
				}
			}
		});
		watcher.start(StartMode.BUILD_INITIAL_CACHE);
		System.out.println("Register zk watcher successfully!");
		Thread.sleep(Integer.MAX_VALUE);
	}
}