package gateway.base.system.service;

import java.util.HashMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import common.constant.ServiceName;
import common.constant.url.SystemConstantUrl;
import gateway.base.system.service.impl.SchedualServiceSystemConstantHystric;
import systemConstant.pojo.dto.GetValsByNameDto;
import systemConstant.pojo.dto.SetSystemConstantDto;
import systemConstant.pojo.dto.SetSystemConstantsDto;

@FeignClient(value = ServiceName.systemConstant, fallback = SchedualServiceSystemConstantHystric.class)
public interface SchedualServiceSystemConstant {

	@PostMapping(value = SystemConstantUrl.root + SystemConstantUrl.getValByName)
	String getValByName(@RequestBody String constantName);
	
	@PostMapping(value = SystemConstantUrl.root + SystemConstantUrl.getValByNameRefresh)
	String getValByNameRefresh(@RequestBody String constantName);
	
	@PostMapping(value = SystemConstantUrl.root + SystemConstantUrl.getValsByName)
	HashMap<String, String> getValsByName(@RequestBody GetValsByNameDto dto);
	
	@PostMapping(value = SystemConstantUrl.root + SystemConstantUrl.getValsByNameRefresh)
	HashMap<String, String> getValsByNameRefresh(@RequestBody GetValsByNameDto constantName);

	@PostMapping(value = SystemConstantUrl.root + SystemConstantUrl.setValByName)
	void setValByName(@RequestBody SetSystemConstantDto constantName);

	@PostMapping(value = SystemConstantUrl.root + SystemConstantUrl.setValsByName)
	void setValsByName(@RequestBody SetSystemConstantsDto constantName);
}
