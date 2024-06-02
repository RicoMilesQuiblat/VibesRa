package com.NeuralN.VibesRa.dto;

import com.NeuralN.VibesRa.model.HotelRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelRoomwithOwnerDTO {
    private HotelRoomDTO hotelRoom;
    private UserDTO user;
    private CheckFavoriteDTO checkFavorite;
}
