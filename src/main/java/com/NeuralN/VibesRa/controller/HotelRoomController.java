package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.dto.HotelRoomDTO;
import com.NeuralN.VibesRa.dto.HotelRoomRequestDTO;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.service.HotelRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;

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
            hotel.setType("type");
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
    public ResponseEntity<HotelRoomDTO> getHotelById(@PathVariable Long id) {
        HotelRoomDTO hotel = hotelRoomService.getRoomById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
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
    public ResponseEntity<HotelRoom> deleteHotel(@RequestParam Long id){
        HotelRoom hotel = hotelRoomService.deleteRoom(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HotelRoomDTO> updateHotel(@Valid @RequestBody HotelRoomRequestDTO requestDTO){
        if (requestDTO.getHotelRoom().getUserId().equals(requestDTO.getUserId())) {
            HotelRoomDTO newRoom = hotelRoomService.saveRoom(requestDTO.getHotelRoom(), requestDTO.getUserId());
            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/latest")
    public ResponseEntity<HotelRoomDTO> getLatestHotelByUserId(@RequestParam Long id) {
        HotelRoomDTO hotels = hotelRoomService.getLatestHotelByUserId(id);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
}


