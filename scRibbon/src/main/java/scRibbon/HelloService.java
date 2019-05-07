package scRibbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import constant.ServiceName;
import constant.Urls;

@Service
public class HelloService {

	@Autowired
	private RestTemplate restTemplate;

	public String hiService(String name) {
		return restTemplate.getForObject("http://" + ServiceName.serviceHi + Urls.hi + "?name=" + name, String.class);
	}
	
}
