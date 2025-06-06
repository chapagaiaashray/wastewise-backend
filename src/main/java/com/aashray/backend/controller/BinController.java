package com.aashray.backend.controller;

import com.aashray.backend.model.BinEntity;
import com.aashray.backend.repository.BinRepository;
import com.aashray.backend.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bins")
public class BinController {

    private final BinRepository binRepository;
    private final RedisService redisService;  // Optional

    @Autowired
    public BinController(BinRepository binRepository, @Autowired(required = false) RedisService redisService) {
        this.binRepository = binRepository;
        this.redisService = redisService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBins() {
        try {
            List<BinEntity> dbBins = binRepository.findAll();

            if (redisService != null) {
                for (BinEntity bin : dbBins) {
                    try {
                        redisService.saveBin(bin.getId(), bin);
                    } catch (Exception e) {
                        System.err.println("Failed to cache bin ID " + bin.getId() + ": " + e.getMessage());
                    }
                }
            }

            return ResponseEntity.ok(dbBins);
        } catch (Exception e) {
            e.printStackTrace(); // will show in Render logs
            return ResponseEntity.internalServerError().body("Server error while fetching bins.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BinEntity> getBinById(@PathVariable Long id) {
        if (redisService != null) {
            try {
                BinEntity cached = redisService.getBin(id);
                if (cached != null) return ResponseEntity.ok(cached);
            } catch (Exception e) {
                System.err.println("Redis get failed for ID " + id + ": " + e.getMessage());
            }
        }

        Optional<BinEntity> dbBin = binRepository.findById(id);
        if (dbBin.isPresent()) {
            if (redisService != null) {
                try {
                    redisService.saveBin(id, dbBin.get());
                } catch (Exception e) {
                    System.err.println("Redis save failed for ID " + id + ": " + e.getMessage());
                }
            }
            return ResponseEntity.ok(dbBin.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BinEntity> updateBin(@PathVariable Long id, @RequestBody BinEntity updatedBin) {
        return binRepository.findById(id)
                .map(bin -> {
                    bin.setLocationName(updatedBin.getLocationName());
                    bin.setFillLevel(updatedBin.getFillLevel());
                    bin.setStatus(updatedBin.getStatus());
                    bin.setType(updatedBin.getType());

                    BinEntity saved = binRepository.save(bin);

                    if (redisService != null) {
                        try {
                            redisService.saveBin(id, saved);
                        } catch (Exception e) {
                            System.err.println("Redis save failed during update for ID " + id + ": " + e.getMessage());
                        }
                    }

                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
