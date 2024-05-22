package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.BookingRepository;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(int bookingID) {
        return bookingRepository.findById(bookingID).orElse(null);
    }

    public Booking saveBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        User user = userRepository.findById(bookingDTO.getUserID()).orElse(null);
        HotelRoom hotel = hotelRoomRepository.findById(bookingDTO.getHotelID()).orElse(null);

        if (user == null || hotel == null) {
            return null;
        }

        booking.setUser(user);
        booking.setHotel(hotel);

        return bookingRepository.save(booking);
    }

    public void deleteBooking(int bookingID) {
        bookingRepository.deleteById(bookingID);
    }


    public Booking updateBooking(int bookingID, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingID).orElse(null);

        if (booking == null) {
            return null;
        }

        User user = userRepository.findById(bookingDTO.getUserID()).orElse(null);

        if (user == null) {
            return null;
        }

        HotelRoom hotel = hotelRoomRepository.findById(bookingDTO.getHotelID()).orElse(null);

        if (hotel == null) {
            return null;
        }

        booking.setUser(user);
        booking.setHotel(hotel);

        return bookingRepository.save(booking);
    }
}
