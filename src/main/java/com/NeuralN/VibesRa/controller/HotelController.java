package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.model.Hotel;
import com.NeuralN.VibesRa.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel){
        Hotel newHotel = hotelService.addHotel(hotel.getName(), hotel.getLocation());
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @GetMapping("/get/allHotels")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> allHotels = hotelService.getAllHotels();
        return new ResponseEntity<>(allHotels, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Hotel> getHotelById(@RequestParam Integer id){
        Hotel hotel = hotelService.getHotelById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/get/location")
    public ResponseEntity<List<Hotel>> getHotelsByLocation(@RequestParam String name){
        List<Hotel> hotels = hotelService.getHotelsByLocation(name);
        return new ResponseEntity<>(hotels,HttpStatus.OK );
    }

    @PostMapping("/delete")
    public ResponseEntity<Hotel> deleteHotel(@RequestParam Integer id){
        Hotel hotel = hotelService.deleteHotel(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateHotel(@RequestBody Hotel hotel){
        Boolean updated = hotelService.updateHotel(hotel);

        if (updated){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}


