package scAppDemo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import constant.Urls;
import scAppDemo.base.domain.po.Roles;
import scAppDemo.test.service.TestService;

@Controller
@RequestMapping({"/", "/test"})
public class TestController {
	
	@Value("${server.port}")
	private String port;
	
	@Value("${neo.hello}")
	private String hello;

	@Autowired
	private TestService testService;
	
	@RequestMapping(Urls.hi)
	@ResponseBody
    public String home(@RequestParam(value = "name", defaultValue = "tester") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
	
	@RequestMapping("hello")
	@ResponseBody
    public String hello(@RequestParam(value = "name", defaultValue = "tester") String name) {
        return "hi " + name + " ,message: " + hello;
    }
	
	@GetMapping("/roleMapper")
	@ResponseBody
	public Roles roleMapper() {
		return testService.roleMapper();
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "testing";
	}
}
