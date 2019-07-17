package imageProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"imageProject.base", "imageProject.config"})
@ComponentScan(basePackages = {"imageProject.test"})
@EnableDiscoveryClient

public class ImagesApplication {
	
	public static void main(String[] args) {
        SpringApplication.run( ImagesApplication.class, args );
    }

}