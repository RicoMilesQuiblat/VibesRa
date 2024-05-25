package com.NeuralN.VibesRa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotelRoomRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;
    private HotelRoomDTO hotelRoom;
}

