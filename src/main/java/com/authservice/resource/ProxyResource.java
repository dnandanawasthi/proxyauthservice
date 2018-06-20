package com.authservice.resource;

import static com.authservice.common.Constants.CARD_SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.authservice.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/services")
public class ProxyResource {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private IUserService userService;

    @RequestMapping
    public String getOne(@RequestParam String url){
        url = url.replaceAll("_","/");
        url = CARD_SERVICE + url;
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        }).getBody();

    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
