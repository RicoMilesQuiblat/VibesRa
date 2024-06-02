package com.NeuralN.VibesRa.dto;

import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.PaymentHistory;
import com.NeuralN.VibesRa.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserBookingDTO {
    private UUID id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingStatus;
    private LocalDate createdAt;
    private User user;
    private HotelRoom hotelRoom;
    private PaymentHistory paymentHistory;
}
