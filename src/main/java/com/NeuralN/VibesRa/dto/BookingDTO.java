package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BookingDTO {
    private int bookingID;
    private int userID;
    private int hotelID;
    private int paymentHistoryID;
}
