package org.javastudy.cache.memcache;

import net.spy.memcached.MemcachedClient;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
/*
 
 * spymemcached也自带了几个例子，大家可以看看
 
 * http://code.google.com/p/spymemcached/wiki/Examples
 
 */

class User implements Serializable { // 必须将对象序列化才能保存
	public String userName;
	public String password;
}

public class Spymemcached {

	public static void main(String[] args) throws IOException {
		MemcachedClient c = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));

		// 存取一个简单的Integer
		// Store a value (async) for one hour
		c.set("someKey", 3600, new Integer(4));
		// Retrieve a value (synchronously).
		Object myObject = c.get("someKey");
		Integer result = (Integer) myObject;
		System.out.println(result);
		// 存取一个序列化的对象
		System.out.println("存之前的时间：" + System.currentTimeMillis());
		User user1 = new User();
		user1.userName = "ZhangSan";
		user1.password = "showList";
		c.set("user1", 3600, user1);
		System.out.println("取之前的时间：" + System.currentTimeMillis());
		User myUser1 = (User) (c.get("user1"));
		System.out.println(myUser1.userName + " " + myUser1.password);
		System.out.println("取之后的时间：" + System.currentTimeMillis());
	}
}