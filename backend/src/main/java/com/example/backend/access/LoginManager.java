package com.example.backend.access;

import com.example.backend.db.PoolingPersistenceManager;

import java.sql.*;

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
                    "INSERT INTO \"HangMan\".public.\"AccessLog\" (data_accesso, username, ora_accesso) VALUES (?, ?, ?)");
            st.setDate(1, currentDate);
            st.setString(2, username);
            st.setTime(3, currentTime);

            ResultSet rs = st.executeQuery();
        } catch (SQLException ex) {
        }
    }

}
