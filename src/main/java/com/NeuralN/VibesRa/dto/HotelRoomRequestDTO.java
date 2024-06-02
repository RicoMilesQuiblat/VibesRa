package com.NeuralN.VibesRa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class HotelRoomRequestDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;
    private HotelRoomDTO hotelRoom;
}

