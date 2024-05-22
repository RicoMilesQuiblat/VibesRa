package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.dto.HotelRoomDTO;
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
    public ResponseEntity<HotelRoomDTO> createRoom(@Valid @RequestBody HotelRoomDTO hotelRoomDTO) {
        HotelRoomDTO newRoom = hotelRoomService.saveRoom(hotelRoomDTO);
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
    public ResponseEntity<HotelRoomDTO> updateHotel(@RequestBody HotelRoomDTO hotel){
        HotelRoomDTO newRoom = hotelRoomService.saveRoom(hotel);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }
}


