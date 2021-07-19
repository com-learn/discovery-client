package com.learn.discoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
//@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class DiscoveryClientApplication {

	
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

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
    RestTemplate restTemplate;

	@Autowired
	Bootclient bootclient;

	@GetMapping("/hello")
	public String sayHello() {

        return "Hello Discovery! \n";
	}
    @GetMapping("/callboot")
	public String callHello() {

        //String url = "http://bootdemo:8080/api/bootdemo/hello";
        //ResponseEntity<String> entity=
         //restTemplate.getForEntity(url, String.class);

         //return "Calling bootdemo from discovery: "+entity.getBody()+"\n";
		 return "Calling bootdmo from discovery: "+bootclient.getHello();
    }
    
  
}
