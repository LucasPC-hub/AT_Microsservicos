package edu.infnet.veiculos.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;

@Component
public class CustomPortSelector implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        // Inicia a verificação a partir da porta 8080
        int availablePort = findAvailablePort(8080);
        factory.setPort(availablePort);  // Configura a porta disponível
        System.out.println("Aplicação iniciada na porta: " + availablePort);
    }

    // Função para encontrar uma porta disponível
    private int findAvailablePort(int startingPort) {
        int port = startingPort;
        while (!isPortAvailable(port)) {
            port++;  // Se a porta não estiver disponível, tenta a próxima
        }
        return port;  // Retorna a primeira porta disponível
    }

    // Verifica se uma porta está disponível
    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;  // Porta está disponível
        } catch (IOException e) {
            return false;  // Porta não está disponível
        }
    }
}
