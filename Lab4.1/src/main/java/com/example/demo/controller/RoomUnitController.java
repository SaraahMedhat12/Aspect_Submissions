package com.example.demo.controller;

import com.example.demo.annotation.CacheLock;
import com.example.demo.annotation.RateGate;
import com.example.demo.entity.RoomUnit;
import com.example.demo.service.RoomUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/room-units")
public class RoomUnitController {

    @Autowired
    private RoomUnitService service;

    @RateGate(maxRequests = 2, period = 10, unit = TimeUnit.SECONDS, keyBase = "roomunit")
    @GetMapping
    public ResponseEntity<List<RoomUnit>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @CacheLock(prefix = "roomunit-lock", keyExpression = "#id", timeout = 15, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/lock/{id}")
    public ResponseEntity<String> lockAndProcess(@PathVariable Long id) {
        return ResponseEntity.ok(service.simulateProcessing(id));
    }

    @PostMapping
    public ResponseEntity<RoomUnit> create(@RequestBody RoomUnit roomUnit) {
        return ResponseEntity.ok(service.save(roomUnit));
    }
}