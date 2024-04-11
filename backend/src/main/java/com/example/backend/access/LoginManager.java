package com.example.backend.access;

import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.Gson;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class LoginManager {

    private static LoginManager manager;
    private final PoolingPersistenceManager persistence;

    private LoginManager() {
        persistence = PoolingPersistenceManager.getPersistenceManager();
    }

    public static LoginManager getManager() {
        if (manager == null) {
            manager = new LoginManager();
        }
        return manager;
    }

    /**
     * @param username
     * @param password
     * @return int:
     * -1 no validato
     * 0 utente normale
     * 1 utente amministratore
     */
    public int validateCredentials(String username, String password) {
        int result = -1;
        try (
                Connection conn = PoolingPersistenceManager
                        .getPersistenceManager()
                        .getConnection();
                PreparedStatement st = conn.prepareStatement(
                        "SELECT * FROM \"HangMan\".public.\"Users\" WHERE username = ? AND password = ?"
                )
        ) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getInt("admin");
            }
        } catch (SQLException ex) {
        }
        return result;
    }

    public void insertAccesso(String username) {
        Date currentDate = new Date(System.currentTimeMillis());
        Time currentTime = new Time(System.currentTimeMillis());
        try (Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection()) {
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO \"HangMan\".public.\"AccessLog\" (\"userId\", date, time) VALUES (?, ?, ?)");
            st.setInt(1, getUsernameId(username));
            st.setDate(2, Date.valueOf(LocalDate.now()));
            st.setTime(3, Time.valueOf(LocalTime.now()));

            ResultSet rs = st.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getUsernameId(String username){
        int id = -1;

        String query = "SELECT id FROM \"HangMan\".public.\"Users\" WHERE username = ?";
        try(
                Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
        ) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, username);
            Gson gson = new Gson();

            ResultSet rs = st.executeQuery();

            if (rs.next()) id = rs.getInt("id");

            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

}
