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
    private final RedisService redisService;

    @Autowired
    public BinController(BinRepository binRepository,
                         @Autowired(required = false) RedisService redisService) {
        this.binRepository = binRepository;
        this.redisService = redisService;
    }

    @GetMapping
    public List<BinEntity> getAllBins() {
        List<BinEntity> dbBins = binRepository.findAll();

        // Cache each bin individually if Redis is available
        if (redisService != null) {
            dbBins.forEach(bin -> redisService.saveBin(bin.getId(), bin));
        }

        return dbBins;
    }

    @GetMapping("/{id}")
    public BinEntity getBinById(@PathVariable Long id) {
        if (redisService != null) {
            BinEntity cached = redisService.getBin(id);
            if (cached != null) return cached;
        }

        Optional<BinEntity> dbBin = binRepository.findById(id);
        dbBin.ifPresent(bin -> {
            if (redisService != null) {
                redisService.saveBin(id, bin);
            }
        });

        return dbBin.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BinEntity> updateBin(@PathVariable Long id, @RequestBody BinEntity updatedBin) {
        return binRepository.findById(id)
            .map(bin -> {
                bin.setLocationName(updatedBin.getLocationName());
                bin.setFillLevel(updatedBin.getFillLevel());
                bin.setStatus(updatedBin.getStatus());
                bin.setType(updatedBin.getType());
                binRepository.save(bin);

                // Update Redis cache if available
                if (redisService != null) {
                    redisService.saveBin(id, bin);
                }

                return ResponseEntity.ok(bin);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
