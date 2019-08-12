package systemConstantProject.systemConstant.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import common.constant.url.SystemConstantUrl;
import systemConstant.pojo.dto.GetValsByNameDto;
import systemConstant.pojo.dto.SetSystemConstantDto;
import systemConstant.pojo.dto.SetSystemConstantsDto;
import systemConstantProject.systemConstant.service.SystemConstantService;

@RestController
@RequestMapping(value = {SystemConstantUrl.root})
public class SystemConstantController {

	@Autowired
	private SystemConstantService constantService;
	
	@PostMapping(SystemConstantUrl.getValByName)
	@ResponseBody
	public String getValByName(@RequestBody String constantName) {
		return constantService.getValByName(constantName);
	}
	
	@PostMapping(SystemConstantUrl.getValByNameRefresh)
	@ResponseBody
	public String getValByNameRefresh(@RequestBody String constantName) {
		return constantService.getValByName(constantName, true);
	}
	
	@PostMapping(SystemConstantUrl.getValsByName)
	@ResponseBody
	public HashMap<String, String> getValsByName(@RequestBody GetValsByNameDto dto) {
		return constantService.getValsByName(dto);
	}
	
	@PostMapping(SystemConstantUrl.getValsByNameRefresh)
	@ResponseBody
	public HashMap<String, String> getValsByNameRefresh(@RequestBody GetValsByNameDto dto) {
		return constantService.getValsByName(dto, true);
	}
	
	@PostMapping(SystemConstantUrl.setValByName)
	@ResponseBody
	public void setValByName(@RequestBody SetSystemConstantDto dto) {
		constantService.setValByName(dto);
	}
	
	@PostMapping(SystemConstantUrl.setValsByName)
	@ResponseBody
	public void setValsByName(@RequestBody SetSystemConstantsDto dto) {
		constantService.setValsByName(dto);
	}
}
