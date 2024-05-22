package com.NeuralN.VibesRa.repository;


import com.NeuralN.VibesRa.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
    Optional<List<HotelRoom>> findByLocation(String location);
}
