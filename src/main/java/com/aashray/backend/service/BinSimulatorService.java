package com.aashray.backend.service;

import com.aashray.backend.model.BinEntity;
import com.aashray.backend.repository.BinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BinSimulatorService {

    private final BinRepository binRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    @Autowired
    public BinSimulatorService(BinRepository binRepository, SimpMessagingTemplate messagingTemplate) {
        this.binRepository = binRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 6000) // every 6 seconds
    public void simulateBinUpdates() {
        List<BinEntity> bins = binRepository.findAll();

        if (bins.isEmpty()) return;

        // Pick 1–3 random bins to update
        int updates = 1 + random.nextInt(Math.min(3, bins.size()));

        for (int i = 0; i < updates; i++) {
            BinEntity bin = bins.get(random.nextInt(bins.size()));

            double newLevel = Math.min(100.0, Math.max(0.0, bin.getFillLevel() + random.nextDouble() * 20 - 10));
            bin.setFillLevel(newLevel);

            binRepository.save(bin);
            messagingTemplate.convertAndSend("/topic/binUpdates", bin);

            System.out.println("[SIMULATION] Updated bin ID " + bin.getId() + " -> " + newLevel + "%");
        }
    }
}
