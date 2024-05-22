package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;

    @PostMapping("/create")
    public ResponseEntity<HotelRoom> addHotel(@RequestBody HotelRoom hotel){
        HotelRoom newHotel = hotelRoomService.addHotel(hotel);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelRoom>> getAllHotels(){
        List<HotelRoom> allHotels = hotelRoomService.getAllHotels();
        return new ResponseEntity<>(allHotels, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<HotelRoom> getHotelById(@RequestParam Integer id){
        HotelRoom hotel = hotelRoomService.getHotelById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/get/location")
    public ResponseEntity<List<HotelRoom>> getHotelsByLocation(@RequestParam String name){
        List<HotelRoom> hotels = hotelRoomService.getHotelsByLocation(name);
        return new ResponseEntity<>(hotels,HttpStatus.OK );
    }

    @PostMapping("/delete")
    public ResponseEntity<HotelRoom> deleteHotel(@RequestParam Integer id){
        HotelRoom hotel = hotelRoomService.deleteHotel(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateHotel(@RequestBody HotelRoom hotel){
        Boolean updated = hotelRoomService.updateHotel(hotel);

        if (updated){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}


