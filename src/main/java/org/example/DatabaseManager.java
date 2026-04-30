package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// This class handles ALL database operations
// One class responsible for connecting, saving, and reading data
public class DatabaseManager {

    // The database file will be created in your project folder
    // "hotel.db" is the name of the SQLite file
    private static final String DB_URL = "jdbc:sqlite:hotel.db";

    // This variable holds our connection to the database
    private Connection connection;

    // ─────────────────────────────────
    // CONNECT TO DATABASE
    // ─────────────────────────────────

    // Opens connection to the database
    // Creates the database file if it does not exist yet
    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database successfully!");
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

    // Closes the database connection when we are done
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
    // CREATE TABLES
    // Creates all tables when app starts
    // IF NOT EXISTS means it won't crash if tables already exist
    // ─────────────────────────────────
    public void createTables() {
        try {
            Statement stmt = connection.createStatement();

            // Table for Rooms
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS rooms (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // auto number ID
                            "room_number TEXT NOT NULL, " +
                            "style TEXT NOT NULL, " +
                            "price REAL NOT NULL, " +       // REAL = decimal number
                            "status TEXT NOT NULL" +
                            ")"
            );

            // Table for Guests
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS guests (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT NOT NULL, " +
                            "email TEXT NOT NULL, " +
                            "phone TEXT NOT NULL, " +
                            "account_type TEXT NOT NULL" +
                            ")"
            );

            // Table for Bookings
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS bookings (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "reservation_number TEXT NOT NULL, " +
                            "guest_name TEXT NOT NULL, " +
                            "room_number TEXT NOT NULL, " +
                            "duration INTEGER NOT NULL, " +  // INTEGER = whole number
                            "status TEXT NOT NULL" +
                            ")"
            );

            // Table for Invoices/Payments
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS invoices (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "amount REAL NOT NULL, " +
                            "payment_method TEXT NOT NULL, " +
                            "details TEXT NOT NULL, " +
                            "status TEXT NOT NULL" +
                            ")"
            );

            // Table for Notifications
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS notifications (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "type TEXT NOT NULL, " +         // EMAIL or POSTAL
                            "recipient TEXT NOT NULL, " +    // email address or postal address
                            "message TEXT NOT NULL, " +
                            "sent_on TEXT NOT NULL" +         // date and time it was sent
                            ")"
            );

            System.out.println("All tables created successfully!");

        } catch (Exception e) {
            System.out.println("Failed to create tables: " + e.getMessage());
        }
    }

    // ─────────────────────────────────
    // SAVE ROOM
    // ─────────────────────────────────
    public boolean saveRoom(String roomNumber, String style, double price, String status) {
        try {
            // PreparedStatement is safer than building SQL string manually
            // ? marks are placeholders filled in below
            String sql = "INSERT INTO rooms (room_number, style, price, status) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Fill in the ? placeholders in order
            pstmt.setString(1, roomNumber);  // first ?
            pstmt.setString(2, style);       // second ?
            pstmt.setDouble(3, price);       // third ?
            pstmt.setString(4, status);      // fourth ?

            pstmt.executeUpdate(); // runs the INSERT command
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
    public boolean saveGuest(String name, String email, String phone, String accountType) {
        try {
            String sql = "INSERT INTO guests (name, email, phone, account_type) VALUES (?, ?, ?, ?)";
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

            pstmt.setString(1, type);       // "EMAIL" or "POSTAL"
            pstmt.setString(2, recipient);  // email or address
            pstmt.setString(3, message);
            pstmt.setString(4, sentOn);     // current date and time

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
    // Returns a list of all rooms as strings for displaying
    // ─────────────────────────────────
    public ArrayList<String> getAllRooms() {
        ArrayList<String> rooms = new ArrayList<>();
        try {
            String sql = "SELECT * FROM rooms";
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // rs.next() moves to next row, returns false when no more rows
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
            String sql  = "SELECT * FROM guests";
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
}
