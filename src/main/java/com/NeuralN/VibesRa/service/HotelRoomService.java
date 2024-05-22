package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelRoomService  {

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    public HotelRoom getHotelById(Integer id) {
        return hotelRoomRepository.findById(id).orElse(null);
    }

    public List<HotelRoom> getHotelsByLocation(String name) {
        return hotelRoomRepository.findByLocation(name).orElse(null);
    }

    public List<HotelRoom> getAllHotels() {
        return hotelRoomRepository.findAll();
    }

    public HotelRoom addHotel(HotelRoom hotel) {
        return hotelRoomRepository.save(hotel);
    }

    public HotelRoom deleteHotel(Integer id) {
        HotelRoom hotel = hotelRoomRepository.findById(id).orElse(null);
        if (hotel != null){
            hotelRoomRepository.deleteById(id);
        }
        return hotel;
    }

    public Boolean updateHotel(HotelRoom hotel) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(hotel.getHotelID()).orElse(null);
        if (hotelRoom != null){
            hotelRoomRepository.save(hotel);
            return true;
        }
        return false;
    }
}
