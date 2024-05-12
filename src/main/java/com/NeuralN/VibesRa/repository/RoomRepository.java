package com.NeuralN.VibesRa.repository;


import com.NeuralN.VibesRa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.NeuralN.VibesRa.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByType(String type);

}