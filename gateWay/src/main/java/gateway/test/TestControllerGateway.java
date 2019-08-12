package gateway.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.constant.url.GateWayUrls;
import common.constant.url.Urls;

@Controller
@RequestMapping(value = GateWayUrls.test)
public class TestControllerGateway {

	@Value("${server.port}")
	private String port;
	
	@Value("${testValue}")
	private String testValue;
	
	@RequestMapping(Urls.hi)
	@ResponseBody
    public String home(@RequestParam(value = "name", defaultValue = "tester") String name) {
        return "hi " + name + " ,i am from port:" + port + ", testValue: " + testValue;
    }
}
