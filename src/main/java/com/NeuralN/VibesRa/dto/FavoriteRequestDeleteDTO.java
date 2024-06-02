package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FavoriteRequestDeleteDTO {
    private UUID favoriteId;
    private UUID userId;
}
