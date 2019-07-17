package imageProject.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class Test {

	@GetMapping("test")
	@ResponseBody
	public String test() {
		return "testing from image project test controller";
	}
}
