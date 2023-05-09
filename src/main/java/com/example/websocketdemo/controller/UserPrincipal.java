/**
 * Copyright (c)  2020~2023, LinkingCloud/DT
 * All rights reserved.
 * Project:spring-boot-websocket-chat-demo
 * Id: UserPrincipal.java   2023-05-09 13:56:01
 * Author: Evan
 */
package com.example.websocketdemo.controller;

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
 * @author Evan
 * <b>Creation Time:</b> 2023-05-09 13:56:01
 * @since V1.0
 */
public class UserPrincipal  implements Principal {

    private  final String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
