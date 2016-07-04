package org.test.service.impl;

import org.springframework.stereotype.Service;
import org.test.service.TestRegistryService;

@Service("testRegistryService")
public class TestRegistryServiceImpl implements TestRegistryService {

	public String hello(String name) {
		return "hello " + name + " welcome to dubbo";
	}
}
