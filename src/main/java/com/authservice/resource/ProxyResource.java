package com.authservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.common.CustomHttpClient;
import com.authservice.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/services")
public class ProxyResource {

    @Autowired
    private IUserService userService;

    @RequestMapping
    public String getOne(@RequestParam String url){
        System.out.println("ProxyResource:getOne triggered..........for url is: "+url);
        url = url.replaceAll("_","/");
        System.out.println("ProxyResource:getOne after change..........for url is: "+url);
        return CustomHttpClient.client(url);
    }




}
