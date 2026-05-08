package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// This class handles ALL database operations
public class DatabaseManager {

    // database file will be created in your project folder
    private static final String DB_URL = "jdbc:sqlite:hotel.db";

    // holds our connection to the database
    private Connection connection;

    // ─────────────────────────────────
    // CONNECT TO DATABASE
    // ─────────────────────────────────
    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database successfully!");
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

    // ─────────────────────────────────
    // DISCONNECT FROM DATABASE
    // ─────────────────────────────────
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (Exception e) {
            System.out.println("Failed to close database: " + e.getMessage());
        }
    }

    // ─────────────────────────────────
    // CREATE ALL TABLES
    // ─────────────────────────────────
    public void createTables() {
        try {
            Statement stmt = connection.createStatement();

            // rooms table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS rooms (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "room_number TEXT NOT NULL, " +
                            "style TEXT NOT NULL, " +
                            "price REAL NOT NULL, " +
                            "status TEXT NOT NULL" +
                            ")"
            );

            // guests table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS guests (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT NOT NULL, " +
                            "email TEXT NOT NULL, " +
                            "phone TEXT NOT NULL, " +
                            "account_type TEXT NOT NULL" +
                            ")"
            );

            // bookings table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS bookings (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "reservation_number TEXT NOT NULL, " +
                            "guest_name TEXT NOT NULL, " +
                            "room_number TEXT NOT NULL, " +
                            "duration INTEGER NOT NULL, " +
                            "status TEXT NOT NULL" +
                            ")"
            );

            // invoices table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS invoices (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "amount REAL NOT NULL, " +
                            "payment_method TEXT NOT NULL, " +
                            "details TEXT NOT NULL, " +
                            "status TEXT NOT NULL" +
                            ")"
            );

            // notifications table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS notifications (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "type TEXT NOT NULL, " +
                            "recipient TEXT NOT NULL, " +
                            "message TEXT NOT NULL, " +
                            "sent_on TEXT NOT NULL" +
                            ")"
            );

            // users table for login system
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "username TEXT NOT NULL UNIQUE, " +
                            "password TEXT NOT NULL, " +
                            "role TEXT NOT NULL" +
                            ")"
            );

            // create default manager account on first run
            // OR IGNORE means if manager already exists nothing happens
            stmt.execute(
                    "INSERT OR IGNORE INTO users (username, password, role) " +
                            "VALUES ('manager', 'manager123', 'MANAGER')"
            );

            System.out.println("All tables created successfully!");

        } catch (Exception e) {
            System.out.println("Failed to create tables: " + e.getMessage());
        }
    }

    // ─────────────────────────────────
    // SAVE ROOM
    // ─────────────────────────────────
    public boolean saveRoom(String roomNumber, String style,
                            double price, String status) {
        try {
            String sql = "INSERT INTO rooms (room_number, style, price, status) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, roomNumber);
            pstmt.setString(2, style);
            pstmt.setDouble(3, price);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            System.out.println("Room saved to database!");
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save room: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────
    // SAVE GUEST
    // ─────────────────────────────────
    public boolean saveGuest(String name, String email,
                             String phone, String accountType) {
        try {
            String sql = "INSERT INTO guests (name, email, phone, account_type) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, accountType);
            pstmt.executeUpdate();
            System.out.println("Guest saved to database!");
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save guest: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────
    // SAVE BOOKING
    // ─────────────────────────────────
    public boolean saveBooking(String reservationNumber, String guestName,
                               String roomNumber, int duration, String status) {
        try {
            String sql = "INSERT INTO bookings " +
                    "(reservation_number, guest_name, room_number, duration, status) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, reservationNumber);
            pstmt.setString(2, guestName);
            pstmt.setString(3, roomNumber);
            pstmt.setInt(4, duration);
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            System.out.println("Booking saved to database!");
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save booking: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────
    // SAVE INVOICE
    // ─────────────────────────────────
    public boolean saveInvoice(double amount, String paymentMethod,
                               String details, String status) {
        try {
            String sql = "INSERT INTO invoices (amount, payment_method, details, status) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setString(2, paymentMethod);
            pstmt.setString(3, details);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            System.out.println("Invoice saved to database!");
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save invoice: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────
    // SAVE NOTIFICATION
    // ─────────────────────────────────
    public boolean saveNotification(String type, String recipient,
                                    String message, String sentOn) {
        try {
            String sql = "INSERT INTO notifications (type, recipient, message, sent_on) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, type);
            pstmt.setString(2, recipient);
            pstmt.setString(3, message);
            pstmt.setString(4, sentOn);
            pstmt.executeUpdate();
            System.out.println("Notification saved to database!");
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save notification: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────
    // GET ALL ROOMS
    // ─────────────────────────────────
    public ArrayList<String> getAllRooms() {
        ArrayList<String> rooms = new ArrayList<>();
        try {
            String sql    = "SELECT * FROM rooms";
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                String room =
                        "Room: "   + rs.getString("room_number") + " | " +
                                "Style: "  + rs.getString("style")       + " | " +
                                "Price: $" + rs.getDouble("price")       + " | " +
                                "Status: " + rs.getString("status");
                rooms.add(room);
            }
        } catch (Exception e) {
            System.out.println("Failed to get rooms: " + e.getMessage());
        }
        return rooms;
    }

    // ─────────────────────────────────
    // GET ALL GUESTS
    // ─────────────────────────────────
    public ArrayList<String> getAllGuests() {
        ArrayList<String> guests = new ArrayList<>();
        try {
            String sql    = "SELECT * FROM guests";
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                String guest =
                        "Name: "  + rs.getString("name")         + " | " +
                                "Email: " + rs.getString("email")        + " | " +
                                "Phone: " + rs.getString("phone")        + " | " +
                                "Type: "  + rs.getString("account_type");
                guests.add(guest);
            }
        } catch (Exception e) {
            System.out.println("Failed to get guests: " + e.getMessage());
        }
        return guests;
    }

    // ─────────────────────────────────
    // GET ALL BOOKINGS
    // ─────────────────────────────────
    public ArrayList<String> getAllBookings() {
        ArrayList<String> bookings = new ArrayList<>();
        try {
            String sql    = "SELECT * FROM bookings";
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                String booking =
                        "Res#: "     + rs.getString("reservation_number") + " | " +
                                "Guest: "    + rs.getString("guest_name")         + " | " +
                                "Room: "     + rs.getString("room_number")        + " | " +
                                "Duration: " + rs.getInt("duration")              + " days | " +
                                "Status: "   + rs.getString("status");
                bookings.add(booking);
            }
        } catch (Exception e) {
            System.out.println("Failed to get bookings: " + e.getMessage());
        }
        return bookings;
    }

    // ─────────────────────────────────
    // GET ALL INVOICES
    // ─────────────────────────────────
    public ArrayList<String> getAllInvoices() {
        ArrayList<String> invoices = new ArrayList<>();
        try {
            String sql    = "SELECT * FROM invoices";
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                String invoice =
                        "Amount: $"  + rs.getDouble("amount")         + " | " +
                                "Method: "   + rs.getString("payment_method") + " | " +
                                "Details: "  + rs.getString("details")        + " | " +
                                "Status: "   + rs.getString("status");
                invoices.add(invoice);
            }
        } catch (Exception e) {
            System.out.println("Failed to get invoices: " + e.getMessage());
        }
        return invoices;
    }

    // ─────────────────────────────────
    // GET ALL NOTIFICATIONS
    // ─────────────────────────────────
    public ArrayList<String> getAllNotifications() {
        ArrayList<String> notifications = new ArrayList<>();
        try {
            String sql    = "SELECT * FROM notifications";
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                String notif =
                        "Type: "      + rs.getString("type")      + " | " +
                                "Recipient: " + rs.getString("recipient") + " | " +
                                "Message: "   + rs.getString("message")   + " | " +
                                "Sent: "      + rs.getString("sent_on");
                notifications.add(notif);
            }
        } catch (Exception e) {
            System.out.println("Failed to get notifications: " + e.getMessage());
        }
        return notifications;
    }

    // ─────────────────────────────────
    // CHECK LOGIN
    // returns role if correct, null if wrong
    // ─────────────────────────────────
    public String checkLogin(String username, String password) {
        try {
            String sql = "SELECT role FROM users " +
                    "WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role"); // "MANAGER" or "RECEPTIONIST"
            }

        } catch (Exception e) {
            System.out.println("Login check failed: " + e.getMessage());
        }
        return null; // login failed
    }

    // ─────────────────────────────────
    // SAVE NEW RECEPTIONIST
    // called by manager to create accounts
    // ─────────────────────────────────
    public boolean saveReceptionist(String username, String password) {
        try {
            // check if username already exists
            String checkSql = "SELECT id FROM users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Username already exists!");
                return false;
            }

            // save the new receptionist
            String sql = "INSERT INTO users (username, password, role) " +
                    "VALUES (?, ?, 'RECEPTIONIST')";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Receptionist created: " + username);
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save receptionist: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────
    // GET ALL RECEPTIONISTS
    // used by manager to see all accounts
    // ─────────────────────────────────
    public ArrayList<String> getAllReceptionists() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql    = "SELECT username FROM users WHERE role = 'RECEPTIONIST'";
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add("👤 " + rs.getString("username") +
                        "  |  Role: RECEPTIONIST");
            }

        } catch (Exception e) {
            System.out.println("Failed to get receptionists: " + e.getMessage());
        }
        return list;
    }

    // ─────────────────────────────────
    // DELETE RECEPTIONIST
    // manager can remove an account
    // ─────────────────────────────────
    public boolean deleteReceptionist(String username) {
        try {
            String sql = "DELETE FROM users " +
                    "WHERE username = ? AND role = 'RECEPTIONIST'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();

            // if rowsAffected is 0 the username was not found
            if (rowsAffected == 0) {
                return false;
            }

            System.out.println("Receptionist deleted: " + username);
            return true;

        } catch (Exception e) {
            System.out.println("Failed to delete receptionist: " + e.getMessage());
            return false;
        }
    }

    public void createUsersTable() {
    }
}
