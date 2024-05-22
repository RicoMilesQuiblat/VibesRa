package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping 
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/get/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        System.out.println("Room: " + room.getHotel().getHotelID());
        Room newRoom = roomService.saveRoom(room);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/available")
    public List<Room> findAvailableRoomsByType(@RequestParam String type) {
        return roomService.findAvailableRoomsByType(type);
    }
}
