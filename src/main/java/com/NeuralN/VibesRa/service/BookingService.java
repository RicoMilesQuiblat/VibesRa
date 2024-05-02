package com.NeuralN.VibesRa.service;


import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.BookingRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;


    public Booking createBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingByUserID(int userid){
        User user = userRepository.findById(userid).get();
        return bookingRepository.findByUser(user);
    }

}
