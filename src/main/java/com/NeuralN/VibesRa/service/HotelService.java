package com.NeuralN.VibesRa.service;


import com.NeuralN.VibesRa.model.Hotel;
import com.NeuralN.VibesRa.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public Hotel addHotel(String name, String location){
        Hotel hotel = new Hotel();
        hotel.setLocation(location);
        hotel.setName(name);
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }
}
