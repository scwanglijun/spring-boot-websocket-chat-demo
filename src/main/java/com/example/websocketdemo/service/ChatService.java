/**
 * Copyright (c)  2020~2023, LinkingCloud/DT
 * All rights reserved.
 * Project:spring-boot-websocket-chat-demo
 * Id: ChatService.java   2023-05-09 13:48:17
 * Author: Evan
 */
package com.example.websocketdemo.service;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.websocketdemo.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

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
 * <b>Creation Time:</b> 2023-05-09 13:48:17
 * @since V1.0
 */
@Slf4j
@Service
public class ChatService {
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;


    public Boolean sendMsg(String msg) {
        try {
            JSONObject msgJson = JSONUtil.parseObj(msg);

            log.info("msgjson:{}",msgJson);
            if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(ChatMessage.MessageType.JOIN.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if("all".equals(msgJson.getStr("to")) &&  msgJson.getStr("type").equals(ChatMessage.MessageType.LEAVE.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if (!"all".equals(msgJson.getStr("to")) &&  msgJson.getStr("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSendToUser(msgJson.getStr("to"),"/topic/msg", msgJson);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
