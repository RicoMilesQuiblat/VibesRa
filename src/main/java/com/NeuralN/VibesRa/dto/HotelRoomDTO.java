package com.NeuralN.VibesRa.dto;

import com.NeuralN.VibesRa.model.Favorite;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class HotelRoomDTO {

    private UUID hotelId;

    @Size(min = 2, message = "Minimum of 3 images required")
    private List<String> roomImages = new ArrayList<>();

    @NotNull(message = "User ID is required")
    private UUID userId;

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
    private String type;

    private String dimension;

    @Min(value = 1, message = "At least 1 bedroom is required")
    private Integer numOfBedrooms = 1;

    @Min(value = 1, message = "At least 1 bathroom is required")
    private Integer numOfBathrooms = 1;

    @Min(value = 1, message = "At least 1 guest is required")
    private Integer numOfGuests = 1;

    private List<AmenityDTO> offeredAmenities = new ArrayList<>();

    private List<Favorite> favorites = new ArrayList<>();

    @NotBlank(message = "Slug is required")
    private String slug;

    private String coverImage;

    @Data
    public static class AmenityDTO {
        private String icon;
        private String amenity;
    }

    private boolean addedCategory;
    private boolean addedDescription;
    private boolean addedLocation;
}