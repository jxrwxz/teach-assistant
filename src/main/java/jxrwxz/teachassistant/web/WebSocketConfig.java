package jxrwxz.teachassistant.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfig  {

//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new ChatTextHandler(), "/chat");
//    }
    @Bean
    public ServerEndpointExporter serverEndpointExpoter(){
        return new ServerEndpointExporter();
    }

}


