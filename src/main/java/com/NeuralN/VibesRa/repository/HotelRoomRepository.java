package com.NeuralN.VibesRa.repository;


import com.NeuralN.VibesRa.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, UUID>, JpaSpecificationExecutor<HotelRoom> {
    Optional<List<HotelRoom>> findByLocation(String location);

    @Query("SELECT hr FROM HotelRoom hr LEFT JOIN FETCH hr.favorites WHERE hr.id = :id")
    Optional<HotelRoom> findByIdWithFavorites(@Param("id") UUID id);

    Optional<HotelRoom> findFirstByUserIdOrderByIdDesc(UUID id);
}
