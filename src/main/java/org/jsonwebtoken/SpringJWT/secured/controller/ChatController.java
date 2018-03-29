package org.jsonwebtoken.SpringJWT.secured.controller;

import org.jsonwebtoken.SpringJWT.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

/*
 * all the messages sent from clients with a destination starting with /app 
 * will be routed to these message handling methods annotated with @MessageMapping
 */

/*
 * For example, a message with destination /app/chat.sendMessage will be routed to the sendMessage() method,
 *  and a message with destination /app/chat.addUser will be routed to the addUser() method
 */

@RestController
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/channel/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/channel/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
