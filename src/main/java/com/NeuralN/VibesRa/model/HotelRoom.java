package com.NeuralN.VibesRa.model;


import com.fasterxml.jackson.annotation.*;
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
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ElementCollection
    @CollectionTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "image_link")
    @Size(min = 3, message = "Minimum of 3 images required")
    private List<String> roomImages = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    private String name;
    private String location;

    private String description;
    private String contact;
    private String email;

//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    private String coverImage;

    @NotBlank(message = "Special Note is required")
    private String specialNote = "Check-in time is 12:00 PM, checkout time is 11:59 AM. If you leave behind any items, please contact the receptionist.";

    private double price;

    private Boolean isBooked = false;

    @NotBlank(message = "Room Type is required")
    private String type;

    private String dimension;

    @Min(value = 1, message = "At least 1 bed is required")
    private Integer numOfBedrooms = 1;

    @Min(value = 1, message = "At least 1 bathroom is required")
    private Integer numOfBathrooms = 1;

    @Min(value = 1, message = "At least 1 guest is required")
    private Integer numOfGuests = 1;

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
    private double rating;

    @JsonManagedReference(value = "hotel-review")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @JsonManagedReference(value = "hotel-favorite")
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    private boolean addedCategory;
    private boolean addedDescription;
    private boolean addedLocation;
}
