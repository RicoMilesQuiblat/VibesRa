package com.NeuralN.VibesRa.repository;


import com.NeuralN.VibesRa.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByName(String name);
    Optional<List<Hotel>> findByLocation(String location);
}
