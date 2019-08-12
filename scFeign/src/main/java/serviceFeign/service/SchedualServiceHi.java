package serviceFeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.constant.ServiceName;
import common.constant.url.DemoUrls;
import common.constant.url.Urls;
import serviceFeign.service.impl.SchedualServiceHiHystric;

@FeignClient(value = ServiceName.hi, fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

	@GetMapping(value = Urls.hi)
	String sayHiFromClientOne(@RequestParam(value = "name") String name);

	@GetMapping(value = DemoUrls.testRoleMapper)
	String testRoleMapper();
}
