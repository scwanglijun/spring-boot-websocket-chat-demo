/**
 * Copyright (c)  2020~2023, LinkingCloud/DT
 * All rights reserved.
 * Project:spring-boot-websocket-chat-demo
 * Id: SendToOneVO.java   2023-05-09 14:16:25
 * Author: Evan
 */
package com.example.websocketdemo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
 * <b>Creation Time:</b> 2023-05-09 14:16:25
 * @since V1.0
 */
@Setter
@Getter
@ToString
public class SendToOneReqVO implements Serializable {

    private String uid;

    private String content;

}
