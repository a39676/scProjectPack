package scAppDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"scAppDemo.base", "scAppDemo.config"})
@ComponentScan(basePackages = {"scAppDemo.test"})
@EnableDiscoveryClient
public class ServiceHiApplication {
	
	public static void main(String[] args) {
        SpringApplication.run( ServiceHiApplication.class, args );
    }

}