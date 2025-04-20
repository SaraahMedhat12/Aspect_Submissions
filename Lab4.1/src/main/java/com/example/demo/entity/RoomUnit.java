package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room_units")
public class RoomUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String category;
    private Double rate;

    public RoomUnit() {}

    public RoomUnit(String label, String category, Double rate) {
        this.label = label;
        this.category = category;
        this.rate = rate;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getlabel() { return label; }
    public void setlabel(String label) { this.label = label; }

    public String getcategory() { return category; }
    public void setcategory(String category) { this.category = category; }

    public Double getrate() { return rate; }
    public void setrate(Double rate) { this.rate = rate; }
}