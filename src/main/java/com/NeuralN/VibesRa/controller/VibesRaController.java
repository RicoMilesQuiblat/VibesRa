package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.model.Hotel;
import com.NeuralN.VibesRa.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vibesra")
@CrossOrigin
public class VibesRaController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/addHotel")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel){
        Hotel newHotel = hotelService.addHotel(hotel.getName(), hotel.getLocation());
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @GetMapping("/allHotels")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> allHotels = hotelService.getAllHotels();
        return new ResponseEntity<>(allHotels, HttpStatus.OK);
    }

    @GetMapping("/hotel")
    public ResponseEntity<Hotel> getHotelByName(@RequestParam String name){
        Hotel hotel = hotelService.getHotelByName(name);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/location")
    public ResponseEntity<List<Hotel>> getHotelsByLocation(@RequestParam String location){
        List<Hotel> hotels = hotelService.getHotelsByLocation(location);
        return new ResponseEntity<>(hotels,HttpStatus.OK );
    }

    @PostMapping("/delete")
    public ResponseEntity<Hotel> deleteHotel(@RequestParam String name){
        Hotel hotel = hotelService.deleteHotel(name);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }
}
