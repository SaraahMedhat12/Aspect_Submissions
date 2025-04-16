package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class CreateEventDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date and time are required")
    private LocalDateTime dateTime;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Host ID is required")
    private Long hostId;

    // === Getters ===
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Long getHostId() {
        return hostId;
    }

    // === Setters ===
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }
}