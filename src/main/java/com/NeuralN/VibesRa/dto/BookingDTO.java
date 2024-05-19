package com.NeuralN.VibesRa.dto;

import java.util.Set;

public class BookingDTO {

    private int userId;
    private int hotelId;
    private Set<Integer> roomIds;

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Set<Integer> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(Set<Integer> roomIds) {
        this.roomIds = roomIds;
    }
}
