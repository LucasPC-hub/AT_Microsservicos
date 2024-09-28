package edu.infnet.veiculos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @Autowired
    private Environment environment;

    @GetMapping("/status")
    public String getStatus() {
        String serverPort = environment.getProperty("local.server.port");
        String response = "Requisição atendida pela instância na porta: " + serverPort;
        System.out.println(response);
        return response;
    }
}