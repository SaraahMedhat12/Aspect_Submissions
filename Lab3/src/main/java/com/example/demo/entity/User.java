package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role; // USER, HOST, ADMIN

    @ManyToMany(mappedBy = "attendees")
    private Set<Event> rsvpedEvents = new HashSet<>();

    // Getters, Setters, Constructors
}