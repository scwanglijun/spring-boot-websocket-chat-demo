package com.example.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.web.socket.config.annotation.*;
import reactor.netty.tcp.SslProvider;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final HeaderParamInterceptor headerParamInterceptor;

    public WebSocketConfig(HeaderParamInterceptor headerParamInterceptor) {
        this.headerParamInterceptor = headerParamInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.interceptors(headerParamInterceptor);
    }

    /***
     * 采用自定义拦截器，获取connect时传递的参数
     * @param registry
     */


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
        //registry.enableSimpleBroker("/topic");   // Enables a simple in-memory broker



        registry.enableStompBrokerRelay("/topic")
                .setAutoStartup(true)
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setVirtualHost("ws_demo")
                .setClientLogin("admin")
                .setClientPasscode("123456")
                .setSystemLogin("admin")
                .setSystemPasscode("123456")
                .setSystemHeartbeatReceiveInterval(5000)
                .setSystemHeartbeatSendInterval(4000);
    }
}
