package gateway.base.system.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import gateway.base.system.service.SchedualServiceSystemConstant;
import systemConstant.pojo.dto.GetValsByNameDto;
import systemConstant.pojo.dto.SetSystemConstantDto;
import systemConstant.pojo.dto.SetSystemConstantsDto;

@Service
public class SchedualServiceSystemConstantHystric implements SchedualServiceSystemConstant {

	@Override
	public String getValByName(@RequestBody String constantName) {
		return "";
	}

	@Override
	public String getValByNameRefresh(String constantName) {
		return "";
	}

	@Override
	public HashMap<String, String> getValsByName(GetValsByNameDto dto) {
		return new HashMap<String, String>();
	}

	@Override
	public HashMap<String, String> getValsByNameRefresh(GetValsByNameDto constantName) {
		return new HashMap<String, String>();
	}
	
	@Override
	public void setValByName(SetSystemConstantDto dto) {
		
	}
	
	@Override
	public void setValsByName(SetSystemConstantsDto dto) {
		
	}

}
