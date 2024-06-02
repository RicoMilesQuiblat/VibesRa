package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.*;
import com.NeuralN.VibesRa.model.Favorite;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.FavoriteRepository;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public Favorite addToFavorites(FavoriteRequestDTO favoriteRequestDTO) {
        Favorite favorite = new Favorite();
        favorite.setUser(userRepository.findById(favoriteRequestDTO.getUserId()).orElse(null));
        favorite.setHotel(hotelRoomRepository.findById(favoriteRequestDTO.getHotelId()).orElse(null));
        if (favorite.getUser() == null || favorite.getHotel() == null) {
            return null;
        }
        return favoriteRepository.save(favorite);
    }

    public boolean removeFromFavorites(FavoriteRequestDeleteDTO favoriteRequestDeleteDTO) {
        Favorite favorite = favoriteRepository.findById(favoriteRequestDeleteDTO.getFavoriteId()).orElse(null);
        if (favorite == null) {
            return false;
        }
        favoriteRepository.delete(favorite);
        return true;
    }

    public FavoriteRequestDTO getFavorites(UUID userId) {
        FavoriteRequestDTO newFavorite = new FavoriteRequestDTO();
        newFavorite.setUserId(userId);
        return newFavorite;
    }

    public Favorite getFavoriteById(UUID id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    public Favorite getFavoriteByUser(FavoriteRequestDTO favoriteRequestDTO) {
        return favoriteRepository.findByUserAndHotelId(userRepository.findById(favoriteRequestDTO.getUserId()).orElse(null), favoriteRequestDTO.getHotelId());
    }

    public List<FavoritesDTO> getAllFavoritesByUser(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        List<Favorite> favorites = favoriteRepository.findAllByUser(user);

        List<FavoritesDTO> favoritesDTO = favorites.stream().map(favorite -> {
            HotelRoom hotel = favorite.getHotel();
            User owner = hotel.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(owner.getId());
            userDTO.setUsername(owner.getUsername());
            userDTO.setFirstname(owner.getFirstname());
            userDTO.setLastname(owner.getLastname());
            userDTO.setEmail(owner.getEmail());
            CheckFavoriteDTO checkFavoriteDTO = new CheckFavoriteDTO();
            checkFavoriteDTO.setFavoriteId(favorite.getId());
            checkFavoriteDTO.setFavorite(true);
            return new FavoritesDTO(userDTO, convertToDTO(hotel, checkFavoriteDTO));
        }).toList();
        return favoritesDTO;
    }

    private HotelRoomWithFavoriteDTO convertToDTO(HotelRoom hotel, CheckFavoriteDTO checkFavoriteDTO) {
        HotelRoomWithFavoriteDTO hotelRoomWithFavoriteDTO = new HotelRoomWithFavoriteDTO();
        hotelRoomWithFavoriteDTO.setId(hotel.getId());
        hotelRoomWithFavoriteDTO.setRoomImages(hotel.getRoomImages());
        hotelRoomWithFavoriteDTO.setUserId(hotel.getUser().getId());
        hotelRoomWithFavoriteDTO.setName(hotel.getName());
        hotelRoomWithFavoriteDTO.setLocation(hotel.getLocation());
        hotelRoomWithFavoriteDTO.setDescription(hotel.getDescription());
        hotelRoomWithFavoriteDTO.setContact(hotel.getContact());
        hotelRoomWithFavoriteDTO.setEmail(hotel.getEmail());
        hotelRoomWithFavoriteDTO.setSpecialNote(hotel.getSpecialNote());
        hotelRoomWithFavoriteDTO.setPrice(hotel.getPrice());
        hotelRoomWithFavoriteDTO.setIsBooked(hotel.getIsBooked());
        hotelRoomWithFavoriteDTO.setType(hotel.getType());
        hotelRoomWithFavoriteDTO.setDimension(hotel.getDimension());
        hotelRoomWithFavoriteDTO.setNumOfBedrooms(hotel.getNumOfBedrooms());
        hotelRoomWithFavoriteDTO.setNumOfBathrooms(hotel.getNumOfBathrooms());
        hotelRoomWithFavoriteDTO.setNumOfGuests(hotel.getNumOfGuests());
        hotelRoomWithFavoriteDTO.setSlug(hotel.getSlug());
        hotelRoomWithFavoriteDTO.setCoverImage(hotel.getCoverImage());
        hotelRoomWithFavoriteDTO.setAddedCategory(hotel.isAddedCategory());
        hotelRoomWithFavoriteDTO.setAddedDescription(hotel.isAddedDescription());
        hotelRoomWithFavoriteDTO.setAddedLocation(hotel.isAddedLocation());
        hotelRoomWithFavoriteDTO.setCheckFavorite(checkFavoriteDTO);
        return hotelRoomWithFavoriteDTO;
    }
}
