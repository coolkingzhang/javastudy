package org.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.service.TestRegistryService;

@Controller
public class TestController {
	@Autowired
	private TestRegistryService testRegistryService;

	@RequestMapping("/test/say")
	@ResponseBody
	public String say() {
		System.out.println(testRegistryService.hello("sam"));
		return testRegistryService.hello("sam");
	}
}
