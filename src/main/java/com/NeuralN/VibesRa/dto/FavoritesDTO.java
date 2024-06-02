package com.NeuralN.VibesRa.dto;

import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesDTO {
    private UserDTO owner;
    private HotelRoomWithFavoriteDTO hotelFavorites;
}
