package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.FavoriteDTO;
import com.NeuralN.VibesRa.dto.HotelRoomDTO;
import com.NeuralN.VibesRa.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public FavoriteDTO addToFavorites(HotelRoomDTO hotelRoomDTO, Long userId) {
        FavoriteDTO newFavorite = new FavoriteDTO();
        newFavorite.setHotelId(hotelRoomDTO.getHotelId());
        newFavorite.setUserId(userId);
        return newFavorite;
    }

    public FavoriteDTO removeFromFavorites(HotelRoomDTO hotelRoomDTO, Long userId) {
        FavoriteDTO newFavorite = new FavoriteDTO();
        newFavorite.setHotelId(hotelRoomDTO.getHotelId());
        newFavorite.setUserId(userId);
        return newFavorite;
    }

    public FavoriteDTO getFavorites(Long userId) {
        FavoriteDTO newFavorite = new FavoriteDTO();
        newFavorite.setUserId(userId);
        return newFavorite;
    }
}
