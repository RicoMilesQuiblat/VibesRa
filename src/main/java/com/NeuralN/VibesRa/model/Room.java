package com.NeuralN.VibesRa.model;


import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomID;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false, targetEntity = Hotel.class)
    @JoinColumn(name = "hotel_id", nullable = false, referencedColumnName = "hotelID")
    private Hotel hotel;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    private String type;

    private double price;

    private boolean availability;

    public Room(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
