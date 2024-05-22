package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BookingDTO {
    private int userId;
    private int hotelId;
    private Set<Integer> roomIds;
}
