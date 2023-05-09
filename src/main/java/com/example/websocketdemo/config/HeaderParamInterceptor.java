/**
 * Copyright (c)  2020~2023, LinkingCloud/DT
 * All rights reserved.
 * Project:spring-boot-websocket-chat-demo
 * Id: HeaderParamInterceptor.java   2023-05-09 13:57:30
 * Author: Evan
 */
package com.example.websocketdemo.config;

import com.example.websocketdemo.controller.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 2020~2023
 * </p>
 * <p>
 * Company/Department: LinkingCloud/DT
 * </p>
 * @author Evan
 * <b>Creation Time:</b> 2023-05-09 13:57:30
 * @since V1.0
 */
@Slf4j
@Component
public class HeaderParamInterceptor  extends ChannelInterceptorAdapter {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                //取出客户端携带的参数
                Object name = ((Map) raw).get("username");
               log.info("name:{}",name);
                if (name instanceof ArrayList) {
                    // 设置当前访问的认证用户
                    accessor.setUser(new UserPrincipal(((ArrayList) name).get(0).toString()));
                }
            }
        }
        return message;
    }
}
