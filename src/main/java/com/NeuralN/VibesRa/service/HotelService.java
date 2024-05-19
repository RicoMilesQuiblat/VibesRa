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

    public Hotel getHotelById(Integer id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty()){
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

    public Hotel deleteHotel(Integer id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isPresent()){
            System.out.println(hotel.get());
            hotelRepository.delete(hotel.get());
            return hotel.get();
        }
        return null;
    }

    public Boolean updateHotel(Hotel hotel){
        Optional<Hotel> hotel1 = hotelRepository.findById(hotel.getHotelID());
        if(hotel1.isPresent()){
            hotelRepository.save(hotel);
            return true;
        }
        return false;
    }

}
