package com.NeuralN.VibesRa.service;

import java.util.List;

import com.NeuralN.VibesRa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

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
}
