package com.aashray.backend.service;

import com.aashray.backend.model.BinEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BinUpdateSimulator {

    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    public BinUpdateSimulator(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMockBinUpdate() {
        BinEntity bin = new BinEntity(
            1L,
            "Spencer Hall",
            35.2044,
            -85.9214,
            40.0 + random.nextDouble() * 60, // random fill level
            "Recyclable",
            "OK"
        );

        messagingTemplate.convertAndSend("/topic/binUpdates", bin);
        System.out.println("[WS] Sent update to /topic/binUpdates");
    }
}
