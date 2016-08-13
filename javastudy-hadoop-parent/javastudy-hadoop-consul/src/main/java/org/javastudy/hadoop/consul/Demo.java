package org.javastudy.hadoop.consul;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.agent.model.Service;


public class Demo {

	public static void main(String[] args) {
		ConsulRawClient client = new ConsulRawClient("192.168.189.205", 8500);
		ConsulClient consul = new ConsulClient(client);
		// 获取所有服务
		Map<String, Service> map = consul.getAgentServices().getValue();
		JSON.toJSONString(map);
	}
}
