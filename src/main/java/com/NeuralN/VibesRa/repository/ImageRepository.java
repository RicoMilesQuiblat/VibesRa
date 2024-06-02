package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    List<Image> findByHotelId(UUID id);
    void deleteByHotelId(UUID id);
}
