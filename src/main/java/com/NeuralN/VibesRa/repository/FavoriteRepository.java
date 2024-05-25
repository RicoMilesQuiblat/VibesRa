package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
