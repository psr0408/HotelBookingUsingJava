package com.jdbc;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private List<Room> rooms = new ArrayList<>();

    // Add a room to the list
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return rooms;
    }
}
