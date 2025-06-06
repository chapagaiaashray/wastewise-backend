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
    private final RedisService redisService;  // optional, may be null

    // Allow RedisService to be optional
    @Autowired
    public BinController(BinRepository binRepository, @Autowired(required = false) RedisService redisService) {
        this.binRepository = binRepository;
        this.redisService = redisService;
    }

    @GetMapping
    public List<BinEntity> getAllBins() {
        List<BinEntity> dbBins = binRepository.findAll();

        if (redisService != null) {
            for (BinEntity bin : dbBins) {
                redisService.saveBin(bin.getId(), bin);
            }
        }

        return dbBins;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BinEntity> getBinById(@PathVariable Long id) {
        // Try Redis cache
        if (redisService != null) {
            BinEntity cached = redisService.getBin(id);
            if (cached != null) return ResponseEntity.ok(cached);
        }

        // Fallback to DB
        Optional<BinEntity> dbBin = binRepository.findById(id);
        if (dbBin.isPresent()) {
            if (redisService != null) {
                redisService.saveBin(id, dbBin.get());
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
                        redisService.saveBin(id, saved);
                    }

                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
