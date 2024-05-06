package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser(User user);
    Booking findByRoom(Room room);
}