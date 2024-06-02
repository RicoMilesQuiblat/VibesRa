package com.NeuralN.VibesRa.dto;

import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class BookingDTO {
    private UUID bookingID;
    private UUID userID;
    private UUID hotelID;
    private UUID paymentHistoryID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingStatus;
    private String description;
    private String location;
    private String coverImage;
    private double price;
}
