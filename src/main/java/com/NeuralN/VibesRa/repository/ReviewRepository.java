package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Review;
import com.NeuralN.VibesRa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
