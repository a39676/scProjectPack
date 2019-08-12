package scRibbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import common.constant.url.Urls;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping(value = Urls.hi)
    public String hi(@RequestParam String name) {
        return helloService.hiService( name );
    }
}