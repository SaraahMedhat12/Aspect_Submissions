package com.example.demo.service;

import com.example.demo.dto.CreateEventDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.UpdateEventDTO;
import java.util.List;

@Service
public class EventService {

    @Autowired private EventRepository eventRepository;
    @Autowired private UserRepository userRepository;

    public Event createEvent(CreateEventDTO dto) {
        User host = userRepository.findById(dto.getHostId())
                .orElseThrow(() -> new RuntimeException("Host not found"));

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setCategory(dto.getCategory());
        event.setDateTime(dto.getDateTime());
        event.setLocation(dto.getLocation());
        event.setDescription(dto.getDescription());
        event.setHost(host);

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    public Event updateEvent(Long id, UpdateEventDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(dto.getTitle());
        event.setCategory(dto.getCategory());
        event.setDateTime(dto.getDateTime());
        event.setLocation(dto.getLocation());
        event.setDescription(dto.getDescription());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        eventRepository.deleteById(id);
    }
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event rsvpToEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.getAttendees().add(user); // Add user to event
        return eventRepository.save(event);
    }
}