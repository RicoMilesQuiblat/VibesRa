package com.NeuralN.VibesRa.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelID;

    private String name;
    private String location;
    private String description;
    private String image;

    public Hotel(int hotelID, String name, String location, String description, String image) {
        this.hotelID = hotelID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public Hotel() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }
}
