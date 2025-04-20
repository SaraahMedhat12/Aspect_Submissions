package com.example.demo.service;

import com.example.demo.entity.RoomUnit;
import com.example.demo.repository.RoomUnitRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class RoomUnitService {

    @Autowired
    private RoomUnitRepository repository;

    @Autowired
    private RedisUtil redisUtil;

    private final ObjectMapper mapper = new ObjectMapper();
    private static final String CACHE_KEY = "roomunits:all";
    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    public List<RoomUnit> getAll() {
        try {
            String json = redisUtil.get(CACHE_KEY);
            if (json != null) {
                System.out.println("Cache hit – returning RoomUnits from Redis.");
                return mapper.readValue(json, new TypeReference<>() {});
            }
        } catch (Exception e) {
            System.out.println("Error reading from Redis: " + e.getMessage());
        }

        System.out.println("Cache miss – querying RoomUnits from database.");
        List<RoomUnit> units = repository.findAll();

        try {
            String json = mapper.writeValueAsString(units);
            redisUtil.put(CACHE_KEY, json, CACHE_TTL);
            System.out.println("RoomUnits cached in Redis with TTL: " + CACHE_TTL.toMinutes() + " minutes.");
        } catch (Exception e) {
            System.out.println("Error writing to Redis cache: " + e.getMessage());
        }

        return units;
    }

    public RoomUnit save(RoomUnit roomUnit) {
        RoomUnit saved = repository.save(roomUnit);
        redisUtil.delete(CACHE_KEY); // Clear cache to keep it fresh
        System.out.println("New RoomUnit saved. Cache invalidated.");
        return saved;
    }

    public String simulateProcessing(Long id) {
        try {
            System.out.println("Processing RoomUnit ID: " + id);
            Thread.sleep(10000); // Simulate heavy processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Processing interrupted.");
        }
        System.out.println("Finished processing RoomUnit ID: " + id);
        return "Finished RoomUnit " + id;
    }
}