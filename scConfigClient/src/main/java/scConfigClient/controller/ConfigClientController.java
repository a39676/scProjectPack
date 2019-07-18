package scConfigClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constant.Urls;

@RestController
@RefreshScope
public class ConfigClientController {

	@Value("${neo.hello}")
	private String hello;
	
	@Value("${testValue}")
	private String testValue;
	
	@RequestMapping(value = Urls.hi)
	public String hi(){
		return hello;
	}
	
	@RequestMapping("testValue")
	public String testValue(){
		return testValue;
	}

}
