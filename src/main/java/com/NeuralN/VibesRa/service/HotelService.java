package com.NeuralN.VibesRa.service;


import com.NeuralN.VibesRa.model.Hotel;
import com.NeuralN.VibesRa.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Hotel getHotelByName(String name){
        Optional<Hotel> hotel = hotelRepository.findByName(name);
        if(!hotel.isPresent()){
            return null;
        }
        return hotel.get();
    }

    public List<Hotel> getHotelsByLocation(String location){
        Optional<List<Hotel>> hotel = hotelRepository.findByLocation(location);
        if(!hotel.isPresent()){
            return  null;
        }
        return hotel.get();
    }

    public Hotel deleteHotel(String name){
        Optional<Hotel> hotel = hotelRepository.findByName(name);
        if(hotel.isPresent()){
            System.out.println(hotel.get());
            hotelRepository.delete(hotel.get());
            return hotel.get();
        }
        return null;
    }

}
