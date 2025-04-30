package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelJdbc {
    String url = "jdbc:mysql://localhost:3306/hotel_booking";
    String uid = "root";
    String pw = "1Pr@#h@nt567";

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, uid, pw);
    }

    public boolean registerUser(String username, String mobile, String email, String password)
            throws SQLException, ClassNotFoundException {
        String query = "insert into users(name, email, mobile, password) values(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, password);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(String email, String password) throws ClassNotFoundException, SQLException {
        String query = "select * from users where email=? and password=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerAdmin(String name, String mobile, String email, String password)
            throws SQLException, ClassNotFoundException {
        String query = "insert into admins (name, email, mobile, password) values(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, password);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginAdmin(String email, String password) throws ClassNotFoundException, SQLException {
        email = email.trim();
        password = password.trim();
        String query = "select * from admins where email=? and password=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRoom(String type, int price, int availability, int adminid) throws Exception {
        String query = "insert into rooms(type,price,availability, admin_id) values(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, type);
            ps.setInt(2, price);
            ps.setInt(3, availability);
            ps.setInt(4, adminid); 
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
  
    public int getAdminId(String email, String password) throws Exception {
        int adminId = -1;

        try (Connection conn = getConnection()) {
            String sql = "SELECT id FROM admins WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                adminId = rs.getInt("id");  // 'id' is your admin_id
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminId;
    }


    public boolean bookRoom(int userId, int roomId, String bookingDate) {
        String insertSQL = "INSERT INTO bookings(user_id, room_id, booking_date) VALUES (?, ?, ?)";
        String updateRoomSQL = "UPDATE rooms SET availability = availability - 1 WHERE id = ? AND availability > 0";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(insertSQL);
                 PreparedStatement ps2 = con.prepareStatement(updateRoomSQL)) {

                ps1.setInt(1, userId);
                ps1.setInt(2, roomId);
                ps1.setDate(3, java.sql.Date.valueOf(bookingDate));
                ps2.setInt(1, roomId);

                System.out.println("Trying to update room availability...");
                int rowsUpdated = ps2.executeUpdate();
                if (rowsUpdated == 0) {
                    System.out.println("No room updated – either room doesn't exist or availability is 0.");
                    con.rollback();
                    return false;
                }

                System.out.println("Room updated. Proceeding to insert booking...");
                ps1.executeUpdate();
                con.commit();
                System.out.println("Booking committed successfully.");
                return true;

            } catch (SQLException e) {
                System.out.println("Error during booking transaction: " + e.getMessage());
                con.rollback();
                return false;
            }

        } catch (Exception e) {
            System.out.println("Connection or commit error: " + e.getMessage());
            return false;
        }
    }

    
    

    public boolean cancelBooking(int bookingId) {
        String query = "update bookings set status='not booked' where id =?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getUserBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "select * from bookings where user_id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setRoomId(rs.getInt("room_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setStatus(rs.getString("status"));
                    bookings.add(booking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public int getUserIdByEmail(String email) throws Exception {
        int userId = -1;
        String query = "SELECT id FROM users WHERE email = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }
        }
        return userId;
    }




    	public List<Room> getAvailableRooms() throws Exception {
    	    List<Room> availableRooms = new ArrayList<>();
    	    String sql = "SELECT id, type, price, availability FROM rooms WHERE availability >= 1";  // Updated query

    	    try (Connection conn = getConnection(); 
    	         PreparedStatement stmt = conn.prepareStatement(sql); 
    	         ResultSet rs = stmt.executeQuery()) {

    	        while (rs.next()) {
    	            int roomId = rs.getInt("id");
    	            String roomType = rs.getString("type");
    	            double price = rs.getDouble("price");
    	            boolean available = rs.getInt("availability") >= 1; // Convert availability to boolean

    	            // Use the constructor with parameters
    	            Room room = new Room(roomId, roomType, price, available);

    	            availableRooms.add(room); // Add to list
    	        }

    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }

    	    return availableRooms;
    	}




    
    
    
    
    public List<Room> getRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int roomId = rs.getInt("id");
                String roomType = rs.getString("type");
                double price = rs.getDouble("price");
                int availability = rs.getInt("availability");
                
                // Create a Room object using the constructor with parameters
                Room room = new Room(roomType, price, availability);
                room.setRoomId(roomId);  // Set the roomId for the room object
                rooms.add(room);  // Add the room to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    
    public void addRoom(Room room) {
        String query = "INSERT INTO rooms (type, price, availability) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(query);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, room.getRoomType());
            stmt.setDouble(2, room.getPrice());
            stmt.setInt(3, room.isAvailable() ? 1 : 0); // Set availability as 1 or 0

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the auto-generated roomId
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        room.setRoomId(generatedKeys.getInt(1)); // Set the generated roomId
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    public static void main(String[] args) throws Exception {
        HotelJdbc jd = new HotelJdbc();
        boolean ans = jd.loginUser("prashant@gmail.com", "prashant123");
        System.out.println(ans);
       int id =  jd.getAdminId("raghav@gmail.com", "raghav@123");
       System.out.println(id);;
    }
}
