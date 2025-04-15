package com.jdbc;

public class Room {
    private int roomId;
    private String roomType;
    private double price;
    private boolean available;

    // Constructor
    public Room(String roomType, double price, int availability) {
        this.roomType = roomType;
        this.price = price;
        this.available = availability > 0; // Assuming availability is represented as 1 for available and 0 for unavailable
    }
    public Room(int roomId, String roomType, double price, boolean available) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.available = available;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
