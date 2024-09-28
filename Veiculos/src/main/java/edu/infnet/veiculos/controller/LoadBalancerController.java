package edu.infnet.veiculos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoadBalancerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test-load-balance")
    public String testLoadBalance() {
        String response = restTemplate.getForObject("http://veiculos/status", String.class);
        return "Load Balancer Response: " + response;
    }
}