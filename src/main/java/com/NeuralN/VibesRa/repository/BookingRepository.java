package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByHotelId(UUID hotelId);
    List<Booking> findByUserId(UUID userId);

    List<Booking> getBookingsByUserId(UUID userId);
}
