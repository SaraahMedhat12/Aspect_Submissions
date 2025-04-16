package com.example.demo.controller;

import com.example.demo.dto.CreateEventDTO;
import com.example.demo.dto.UpdateEventDTO;
import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEventDTO dto) {
        Event created = eventService.createEvent(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id,
                                             @Valid @RequestBody UpdateEventDTO dto) {
        Event updated = eventService.updateEvent(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/rsvp")
    public ResponseEntity<Event> rsvpToEvent(
            @PathVariable Long eventId,
            @RequestParam Long userId) {
        Event updated = eventService.rsvpToEvent(eventId, userId);
        return ResponseEntity.ok(updated);
    }
}