package com.learn.discoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
@EnableDiscoveryClient
@SpringBootApplication
public class DiscoveryClientApplication {

	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryClientApplication.class, args);
	}

	
}

@RestController
@RequestMapping("/discovery")
class DiscoveryController {

    @Autowired 
    RestTemplate restTemplate;
	@GetMapping("/hello")
	public String sayHello() {

        return "Hello Discovery";
	}
    @GetMapping("/callboot")
	public String callHello() {

        String url = "http://bootdemo:8080/hello";
        ResponseEntity<String> entity=
         restTemplate.getForEntity(url, String.class);

         return "Calling bootdemo from discovery: "+entity.getBody();
    }
    
  
}