package com.zl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @Description: 配置WebSocket
 * @Author: CaoXiaoLong create on 2017/8/28 16:06.
 */
@Configuration
@EnableWebSocketMessageBroker //1
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/my-websocket").withSockJS(); //注册一个Stomp的节点（endpoint）,并指定使用SockJS协议
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); //配置消息代理（MessageBroker）
        config.setApplicationDestinationPrefixes("/app"); //配置了以“/app”开头的websocket请求url
    }

    /*(1)@EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，
         这时候控制器（controller）开始支持@MessageMapping,就像是使用@requestMapping一样。*/
}
