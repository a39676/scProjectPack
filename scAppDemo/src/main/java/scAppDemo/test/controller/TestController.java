package scAppDemo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import constant.Urls;
import scAppDemo.base.domain.po.Roles;
import scAppDemo.mq.send.AckProducer;
import scAppDemo.mq.send.SimpleSender;
import scAppDemo.test.service.TestService;

@Controller
@RefreshScope
@RequestMapping({"/", "/test"})
public class TestController {
	
	@Value("${server.port}")
	private String port;

	@Autowired
	private TestService testService;
	
	@Autowired
	private SimpleSender sender;
	
	@Autowired
	private AckProducer ackProducer;
	
	@RequestMapping(Urls.hi)
	@ResponseBody
    public String home(@RequestParam(value = "name", defaultValue = "tester") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
	
	@GetMapping("/roleMapper")
	@ResponseBody
	public Roles roleMapper() {
		return testService.roleMapper();
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "testing" ;
	}
	
	@GetMapping("/mqSender")
	@ResponseBody
	public String mqSender(@RequestParam(value = "m", defaultValue = "tester") String m) {
		sender.send(m);
		return m;
	}
	
	@GetMapping("/ackSender")
	@ResponseBody
	public String ackSender(@RequestParam(value = "m", defaultValue = "tester") String m) {
		ackProducer.send(m);
		return m;
	}
	
	@GetMapping("/mqConfig")
	@ResponseBody
	public String mqConfig() {
		String m = "host: " + host + " port: " + port + " userName: " + username + " password: " + password;
		return m;
	}
	
	@Value("${mqhost}")
	private String host;
	@Value("${mqport}")
	private String mqPort;
	@Value("${mqusername}")
	private String username;
	@Value("${mqpassword}")
	private String password;
	
}
