package com.learn.discoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
//@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class DiscoveryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryClientApplication.class, args);
		
	}

}
@FeignClient(name = "bootdemo")
interface Bootclient {
    @GetMapping("/api/bootdemo/hello")
    String getHello();
}

@RestController
@RequestMapping("/api/discovery")
class DiscoveryController {

	@Autowired
	Bootclient bootclient;
    
	@Autowired
	Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    
	@GetMapping("/hello")
	public String sayHello() {

        return "Hello Discovery! \n";
	}
    @GetMapping("/callboot")
	public String callHello() {

        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
        
		String bootHello = 
		circuitBreaker.run(
			() -> { return bootclient.getHello();}, 
			throwable -> fallbackHello());
		 return "Calling bootdmo from discovery: "+ bootHello;
    }
    
	private String fallbackHello(){
		return "Bootdemo unavailable; please try again later \n";
	}
}
