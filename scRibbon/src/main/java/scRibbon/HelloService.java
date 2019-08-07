package scRibbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import constant.Urls;

@Service
public class HelloService {

	@Value("${service.name.hi")
	private String serviceHi;
	
	@Autowired
	private RestTemplate restTemplate;

	public String hiService(String name) {
		return restTemplate.getForObject("http://" + serviceHi + Urls.hi + "?name=" + name, String.class);
	}
	
}
