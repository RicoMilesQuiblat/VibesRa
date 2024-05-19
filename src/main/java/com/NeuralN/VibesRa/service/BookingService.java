package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Hotel;
import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.BookingRepository;
import com.NeuralN.VibesRa.repository.HotelRepository;
import com.NeuralN.VibesRa.repository.RoomRepository;
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
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(int bookingID) {
        return bookingRepository.findById(bookingID).orElse(null);
    }

    public Booking saveBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        User user = userRepository.findById(bookingDTO.getUserId()).orElse(null);
        Hotel hotel = hotelRepository.findById(bookingDTO.getHotelId()).orElse(null);

        if (user == null || hotel == null) {
            return null;
        }

        Set<Room> rooms = new HashSet<>();
        for (Integer roomId : bookingDTO.getRoomIds()) {
            Room room = roomRepository.findById(roomId).orElse(null);
            if (room != null) {
                rooms.add(room);
            }
        }

        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setRooms(rooms);

        return bookingRepository.save(booking);
    }

    public void deleteBooking(int bookingID) {
        bookingRepository.deleteById(bookingID);
    }

    public Booking updateBooking(int bookingID, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingID).orElse(null);
        if (booking != null) {
            User user = userRepository.findById(bookingDTO.getUserId()).orElse(null);
            Hotel hotel = hotelRepository.findById(bookingDTO.getHotelId()).orElse(null);

            if (user == null || hotel == null) {
                return null;  // Handle this appropriately in a real application
            }

            Set<Room> rooms = new HashSet<>();
            for (Integer roomId : bookingDTO.getRoomIds()) {
                Room room = roomRepository.findById(roomId).orElse(null);
                if (room != null) {
                    rooms.add(room);
                }
            }

            booking.setUser(user);
            booking.setHotel(hotel);
            booking.setRooms(rooms);

            return bookingRepository.save(booking);
        }
        return null;
    }
}
