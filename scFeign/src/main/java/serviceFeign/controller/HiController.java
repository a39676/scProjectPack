package serviceFeign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import constant.DemoUrls;
import constant.Urls;
import serviceFeign.service.SchedualServiceHi;

@RestController
public class HiController {

	@Autowired
	private SchedualServiceHi schedualServiceHi;

	@Value("${testValueInFeign}")
	private String testValue;
	
	@GetMapping(value = Urls.hi)
	public String sayHi(@RequestParam String name) {
		return schedualServiceHi.sayHiFromClientOne(name) + "; testValue: " + testValue;
	}
	
	@GetMapping(value = DemoUrls.testRoleMapper)
	public String testRoleMapper() {
		return schedualServiceHi.testRoleMapper();
	}
}