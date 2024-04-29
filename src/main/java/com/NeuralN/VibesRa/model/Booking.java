package com.NeuralN.VibesRa.model;

import jakarta.persistence.*;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Room> rooms;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private double totalPrice;

    public Booking(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public List<Room> getRoom() {
        return rooms;
    }

    public void setRoom(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoomToBooking(Room room){
        rooms.add(room);
    }
}
