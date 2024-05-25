package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByHotelId(Long id);
    void deleteByHotelId(Long id);
}
