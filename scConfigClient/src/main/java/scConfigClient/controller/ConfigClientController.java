package scConfigClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import common.constant.url.Urls;

@RestController
@RefreshScope
public class ConfigClientController {

	@Value("${service.name.hi}")
	private String serviceNameHi;
	
	@RequestMapping(value = Urls.hi)
	public String hi(){
		return serviceNameHi;
	}

}
