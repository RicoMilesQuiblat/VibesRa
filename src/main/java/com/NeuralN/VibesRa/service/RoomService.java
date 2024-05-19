package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Hotel;
import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.repository.HotelRepository;
import com.NeuralN.VibesRa.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Integer roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Integer roomId) {
        roomRepository.deleteById(roomId);
    }

    public List<Room> findAvailableRoomsByType(String type) {
        return roomRepository.findByType(type);
    }

    public Room addRoom(Room room) {
        Hotel hotel = room.getHotel();
        if (hotel != null && hotel.getHotelID() != 0) {
            Optional<Hotel> existingHotel = hotelRepository.findById(hotel.getHotelID());
            if (existingHotel.isPresent()) {
                room.setHotel(existingHotel.get());
            } else {
                throw new RuntimeException("Hotel with ID " + hotel.getHotelID() + " not found.");
            }
        } else {
            throw new RuntimeException("Hotel ID must be provided.");
        }
        return roomRepository.save(room);
    }
}
