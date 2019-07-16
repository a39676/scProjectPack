package serviceFeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import constant.DemoUrls;
import constant.ServiceName;
import constant.Urls;
import serviceFeign.service.impl.SchedualServiceHiHystric;

@FeignClient(value = ServiceName.serviceHi, fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

	@GetMapping(value = Urls.hi)
	String sayHiFromClientOne(@RequestParam(value = "name") String name);

	@GetMapping(value = DemoUrls.testRoleMapper)
	String testRoleMapper();
}
