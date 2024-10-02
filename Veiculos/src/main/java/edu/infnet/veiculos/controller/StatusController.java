package edu.infnet.veiculos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class StatusController {

    @Autowired
    private Environment environment;

    @GetMapping("/status")
    public String getStatus() {
        String serverPort = environment.getProperty("local.server.port");
        String ipAddress = "Unknown";
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String response = "Requisição atendida pela instância no IP: " + ipAddress + " e porta: " + serverPort;
        System.out.println(response);
        return response;
    }
}