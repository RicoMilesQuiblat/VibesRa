package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.FavoriteDTO;
import com.NeuralN.VibesRa.dto.HotelRoomDTO;
import com.NeuralN.VibesRa.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/create")
    public ResponseEntity<FavoriteDTO> addToFavorites(@Valid @RequestBody HotelRoomDTO hotelRoomDTO, @RequestBody Long userId) {
        FavoriteDTO newFavorite = favoriteService.addToFavorites(hotelRoomDTO, userId);
        return new ResponseEntity<>(newFavorite, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<FavoriteDTO> removeFromFavorites(@Valid @RequestBody HotelRoomDTO hotelRoomDTO, @RequestBody Long userId) {
        FavoriteDTO newFavorite = favoriteService.removeFromFavorites(hotelRoomDTO, userId);
        return new ResponseEntity<>(newFavorite, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> getFavorites(@RequestBody Long userId) {
        FavoriteDTO newFavorite = favoriteService.getFavorites(userId);
        return new ResponseEntity<>(newFavorite, HttpStatus.OK);
    }
}
