package org.javastudy.rpc.fastjson;

import org.javastudy.rpc.fastjson.bean.User;
import com.alibaba.fastjson.JSON;

public class FastjsonTest {
	public static void main(String[] args) {
		User user = new User();
		user.setUid(10);
		user.setUname("zhangzhihe");
		String jsonString = JSON.toJSONString(user);
		System.out.println(jsonString);
	}
}
