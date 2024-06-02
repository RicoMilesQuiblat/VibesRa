package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Favorite;
import com.NeuralN.VibesRa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Favorite findByUserAndHotelId(User user, UUID hotelId);
    void deleteByUserAndHotelId(User user, UUID hotelId);

    boolean existsByUserAndHotelId(User user, UUID hotelId);

    Favorite findByUserIdAndHotelId(UUID user, UUID hotelId);

    List<Favorite> findAllByUser(User user);
}
