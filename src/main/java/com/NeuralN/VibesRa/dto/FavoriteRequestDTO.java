package com.NeuralN.VibesRa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequestDTO {
    private UUID userId;
    private UUID hotelId;

}
