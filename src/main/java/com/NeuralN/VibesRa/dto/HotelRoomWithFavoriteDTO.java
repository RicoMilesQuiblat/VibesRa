package com.NeuralN.VibesRa.dto;

import com.NeuralN.VibesRa.model.HotelRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomWithFavoriteDTO {
    private UUID id;
    private List<String> roomImages;
    private UUID userId;
    private String name;
    private String location;
    private String description;
    private String contact;
    private String email;
    private String specialNote;
    private double price;
    private Boolean isBooked;
    private String type;
    private String dimension;
    private Integer numOfBedrooms;
    private Integer numOfBathrooms;
    private Integer numOfGuests;
    private String slug;
    private String coverImage;
    private boolean addedCategory;
    private boolean addedDescription;
    private boolean addedLocation;
    private CheckFavoriteDTO checkFavorite;
}
