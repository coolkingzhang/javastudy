package org.javastudy.cachen.couchbase;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import net.spy.memcached.internal.OperationFuture;
import com.couchbase.client.CouchbaseClient;

public class ClientSet {

	// 文档key
	public static final String KEY = "testkey1";
	// expiration time of the document (use 0 to persist forever)
	// 过期时间(单位毫秒 0 永久)
	public static final int EXP_TIME = 0;
	// 文档值
	public static final String VALUE = "{\"myname\":\"EugeneQiu\"," + "\"updated\":\"2013-08-23 13:47:00\","
			+ "\"description\":\"Just a simple test\"," + "\"myblog\":\"http://my.oschina.net/EugeneQiu\"}";

	public static void main(String args[]) {
		List<URI> uris = new LinkedList<URI>();
		// 服务器地址(可在Couchbase后台Server NODES中查看)
		uris.add(URI.create("http://127.0.0.1:8091/pools"));
		CouchbaseClient client = null;
		try {
			// 在Couchbase后台的Data Buckets中查看
			client = new CouchbaseClient(uris, "default", "");
		} catch (IOException e) {
			System.err.println("IOException connecting to Couchbase: " + e.getMessage());
			System.exit(1);
		}
		OperationFuture<Boolean> setOp = client.set(KEY, EXP_TIME, VALUE);
		// 检查是否设置成功
		try {
			if (setOp.get().booleanValue()) {
				System.out.println("Set Succeeded");
			} else {
				System.err.println("Set failed: " + setOp.getStatus().getMessage());
			}
		} catch (InterruptedException e) {
			System.err.println("InterruptedException while doing set: " + e.getMessage());
		} catch (ExecutionException e) {
			System.err.println("ExecutionException while doing set: " + e.getMessage());
		}
		System.out.println();
		// 完成操作后3秒后关闭client
		client.shutdown(3, TimeUnit.SECONDS);
		System.exit(0);
	}
}
