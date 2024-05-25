package com.NeuralN.VibesRa.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "image_link")
    @Size(min = 3, message = "Minimum of 3 images required")
    private List<String> roomImages = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    private String name;
    private String location;
    private String description;
    private String contact;
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;

    private String coverImage;

    @Embeddable
    @Data
    public static class CoverImage {
        private String url;
        private String file;
    }

    @NotBlank(message = "Special Note is required")
    private String specialNote = "Check-in time is 12:00 PM, checkout time is 11:59 AM. If you leave behind any items, please contact the receptionist.";

    private double price;

    private Boolean isBooked = false;

    @NotBlank(message = "Room Type is required")
    private String type = "basic";

    private String dimension;

    @Min(value = 1, message = "At least 1 bed is required")
    private Integer numberOfBeds = 1;

    @ElementCollection
    @CollectionTable(name = "room_amenities", joinColumns = @JoinColumn(name = "room_id"))
    private List<Amenity> offeredAmenities = new ArrayList<>();

    @NotBlank(message = "Slug is required")
    private String slug;

    @Embeddable
    @Data
    public static class Amenity {
        private String icon;
        private String amenity;
    }

    @Max(value = 5, message = "Rating must be between 0 and 5")
    private double rating = 0;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    private boolean addedCategory;
    private boolean addedDescription;
    private boolean addedLocation;
}
