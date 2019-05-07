package serviceFeign.service.impl;

import org.springframework.stereotype.Service;

import serviceFeign.service.SchedualServiceHi;

@Service
public class SchedualServiceHiHystric implements SchedualServiceHi {

	@Override
	public String sayHiFromClientOne(String name) {
		return "sorry " + name;
	}

}
