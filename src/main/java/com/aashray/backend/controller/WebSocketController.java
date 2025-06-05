package com.aashray.backend.controller;

import com.aashray.backend.model.BinEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/binUpdates")
    public BinEntity handleHandshake(BinEntity dummy) {
        System.out.println("[WS] Handshake received at /app/hello");
        return dummy;
    }
}