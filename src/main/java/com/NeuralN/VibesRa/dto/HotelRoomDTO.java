package com.NeuralN.VibesRa.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelRoomDTO {

    private Long hotelID;

    @Size(min = 3, message = "Minimum of 3 images required")
    private List<String> images = new ArrayList<>();

    private String name;
    private String location;
    private String description;
    private String contact;
    private String email;

    @NotBlank(message = "Special Note is required")
    private String specialNote = "Check-in time is 12:00 PM, checkout time is 11:59 AM. If you leave behind any items, please contact the receptionist.";

    private double price;

    private Boolean isBooked = false;

    @NotBlank(message = "Room Type is required")
    private String type = "basic";

    private String dimension;

    @Min(value = 1, message = "At least 1 bed is required")
    private Integer numberOfBeds = 1;

    private List<AmenityDTO> offeredAmenities = new ArrayList<>();

    @NotBlank(message = "Slug is required")
    private String slug;

    private CoverImageDTO coverImage;

    @Data
    public static class CoverImageDTO {
        private String url;
        private String file;
    }

    @Data
    public static class AmenityDTO {
        private String icon;
        private String amenity;
    }
}