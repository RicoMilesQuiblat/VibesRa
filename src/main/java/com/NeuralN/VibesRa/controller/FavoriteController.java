package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.*;
import com.NeuralN.VibesRa.model.Favorite;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/create")
    public ResponseEntity<Favorite> addToFavorites(@Valid @RequestBody FavoriteRequestDTO favoriteRequestDTO) {
        return new ResponseEntity<>(favoriteService.addToFavorites(favoriteRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Favorite> removeFromFavorites(@Valid @RequestBody FavoriteRequestDeleteDTO favoriteRequestDeleteDTO) {
        boolean deleted = favoriteService.removeFromFavorites(favoriteRequestDeleteDTO);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<FavoriteRequestDTO> getFavorites(@RequestBody UUID userId) {
        FavoriteRequestDTO newFavorite = favoriteService.getFavorites(userId);
        return new ResponseEntity<>(newFavorite, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favorite> getFavoriteById(@PathVariable UUID id) {
        Favorite favorite = favoriteService.getFavoriteById(id);
        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<FavoritesDTO>> getAllFavoritesByUser(@RequestParam UUID userId) {
        return new ResponseEntity<>(favoriteService.getAllFavoritesByUser(userId), HttpStatus.OK);
    }

}
