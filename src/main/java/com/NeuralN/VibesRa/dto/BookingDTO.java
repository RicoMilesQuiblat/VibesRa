package com.NeuralN.VibesRa.dto;

import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookingDTO {
    private Long bookingID;
    private Long userID;
    private Long hotelID;
    private Long paymentHistoryID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int noOfRooms;
    private int noOfAdults;
    private int noOfChildren;
    private String bookingStatus;
}
