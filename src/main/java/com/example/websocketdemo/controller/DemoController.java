/**
 * Copyright (c)  2020~2023, LinkingCloud/DT
 * All rights reserved.
 * Project:spring-boot-websocket-chat-demo
 * Id: DemoController.java   2023-05-09 14:13:56
 * Author: Evan
 */
package com.example.websocketdemo.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.websocketdemo.config.RabbitConfig;
import com.example.websocketdemo.controller.vo.SendToOneReqVO;
import com.example.websocketdemo.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
 *
 * @author Evan
 * <b>Creation Time:</b> 2023-05-09 14:13:56
 * @since V1.0
 */
@Slf4j
@RestController
public class DemoController {

    private final RabbitTemplate rabbitTemplate;

    public DemoController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/sendToOne")
    public void sendToOne(@RequestBody SendToOneReqVO reqVO) {
        ChatMessage message = new ChatMessage();
        message.setType(ChatMessage.MessageType.CHAT);
        message.setContent(reqVO.getContent());
        message.setTo(reqVO.getUid());
        message.setSender("系统消息");
        this.sendMessage(message);
    }

    /**
     * 接收 客户端传过来的消息 通过setSender和type 来判别时单发还是群发
     *
     * @param chatMessage
     * @param principal
     */
    @MessageMapping("/chat.sendMessageTest")
    public void sendMessageTest(@Payload ChatMessage chatMessage, Principal principal) {
        try {

            String name = principal.getName();
            chatMessage.setSender(name);
            this.sendMessage(chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 接收 客户端传过来的消息 上线消息
     *
     * @param chatMessage
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {

        log.info("有用户加入到了websocket 消息室:{}", chatMessage.getSender());
        try {
            chatMessage.setType(ChatMessage.MessageType.JOIN);
            log.info(chatMessage.toString());
            chatMessage.setTo("all");
            this.sendMessage(chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 发送消息
     *
     * @param message
     */
    private void sendMessage(ChatMessage message) {
        JSON json = JSONUtil.parse(message);
        log.info("json:{}", json.toString());
        rabbitTemplate.convertAndSend(RabbitConfig.MQ_EXCHANGE, RabbitConfig.MSG_TOPIC_KEY, json.toString());
    }
}
