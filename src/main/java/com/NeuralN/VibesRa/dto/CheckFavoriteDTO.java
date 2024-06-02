package com.NeuralN.VibesRa.dto;

import com.NeuralN.VibesRa.model.Favorite;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckFavoriteDTO {
    private UUID favoriteId;
    private boolean isFavorite;

}
