package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.dto.*;
import com.NeuralN.VibesRa.model.Favorite;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.service.FavoriteService;
import com.NeuralN.VibesRa.service.HotelRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotel")
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;

    @Autowired
    private FavoriteService favoriteService;


    @PostMapping("/create")
    public ResponseEntity<HotelRoomDTO> createRoom(@Valid @RequestBody HotelRoomRequestDTO requestDTO) {
        if (requestDTO.getHotelRoom().getName() == null) {
            HotelRoomDTO hotel = new HotelRoomDTO();
            hotel.setSlug("slug");
            hotel.setUserId(requestDTO.getUserId());
            hotel.setRoomImages(null);
            hotel.setContact("contact");
            hotel.setEmail("email");
            hotel.setDescription("description");
            hotel.setLocation("location");
            hotel.setName("name");
            hotel.setPrice(0.0);
            hotel.setSpecialNote("specialNote");
            hotel.setType("");
            hotel.setCoverImage("coverImage");
            requestDTO.setHotelRoom(hotel);
        }
        HotelRoomDTO newRoom = hotelRoomService.saveRoom(requestDTO.getHotelRoom(), requestDTO.getUserId());
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @GetMapping
    public List<HotelRoomDTO> getAllRooms() {
        return hotelRoomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelRoomwithOwnerDTO> getHotelById(@PathVariable UUID id) {
        HotelRoomDTO hotel = hotelRoomService.getRoomById(id);
        UserDTO user = hotelRoomService.getUserById(hotel.getUserId());
        CheckFavoriteDTO checkFavoriteDTO = new CheckFavoriteDTO();
        Favorite favorite = favoriteService.getFavoriteByUser(new FavoriteRequestDTO(hotel.getUserId(), user.getUserId()));
        if (favorite != null) {
            checkFavoriteDTO.setFavoriteId(favorite.getId());
            checkFavoriteDTO.setFavorite(true);
        } else {
            checkFavoriteDTO.setFavorite(false);
        }
        HotelRoomwithOwnerDTO hotelWithOwner = new HotelRoomwithOwnerDTO(hotel, user, checkFavoriteDTO);
        return new ResponseEntity<>(hotelWithOwner, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<HotelRoomDTO>> getHotelsByLocation(@RequestParam String name) {
        Optional<List<HotelRoomDTO>> hotels = hotelRoomService.getHotelsByLocation(name);
        if (hotels.isPresent() && !hotels.get().isEmpty()) {
            return new ResponseEntity<>(hotels.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HotelRoom> deleteHotel(@RequestParam UUID id){
        hotelRoomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HotelRoomDTO> updateHotel(@Valid @RequestBody HotelRoomRequestDTO requestDTO){
        System.out.println(requestDTO.getHotelRoom().getUserId() + " " + requestDTO.getUserId());
        if (requestDTO.getHotelRoom().getUserId().equals(requestDTO.getUserId())) {
            HotelRoomDTO updatedRoom = hotelRoomService.updateRoom(requestDTO.getHotelRoom());
            if (updatedRoom != null) {
                return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/latest")
    public ResponseEntity<HotelRoomDTO> getLatestHotelByUserId(@RequestParam UUID id) {
        HotelRoomDTO hotels = hotelRoomService.getLatestHotelByUserId(id);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<HotelRoomDTO>> getHotelsByUserId(@RequestParam UUID userId) {
        List<HotelRoomDTO> hotels = hotelRoomService.getHotelsByUserId(userId);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<HotelRoomwithOwnerDTO> getHotelRooms(
            @RequestParam(required = false) Boolean addedCategory,
            @RequestParam(required = false) Boolean addedLocation,
            @RequestParam(required = false) Boolean addedDescription,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer guest,
            @RequestParam(required = false) Integer room,
            @RequestParam(required = false) Integer bathroom,
            @RequestParam(required = false) UUID userId
    ) {
        return hotelRoomService.findHotelRooms(addedCategory, addedLocation, addedDescription, type, location, guest, room, bathroom, userId);
    }
}


