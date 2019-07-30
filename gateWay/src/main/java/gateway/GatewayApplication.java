package gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"gateway.base", "gateway.config"})
@ComponentScan(basePackages = {"gateway.test"})
@EnableDiscoveryClient
public class GatewayApplication {
	
	public static void main(String[] args) {
        SpringApplication.run( GatewayApplication.class, args );
    }

}