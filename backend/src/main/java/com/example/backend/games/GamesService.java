package com.example.backend.games;

import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GamesService {
    public static final String GAMES_PATH = "/game";

    public JsonObject saveGame(String username, String word, boolean winner){
        int usernameId = getUsernameId(username);
        int wordId = getWordId(word);
        System.out.println(usernameId);
        System.out.println(wordId);

        JsonObject result = errorJsonWord();

        String query = "INSERT INTO  \"HangMan\".public.\"Games\" (\"wordId\", \"userId\", winner) VALUES (?, ?, ?)";
        String query1 = "UPDATE \"HangMan\".public.\"Stats\" SET " + ((winner) ? "wins = wins + 1 " : "loses = loses + 1 ") + "WHERE id = ?";
        try (
                Connection conn = PoolingPersistenceManager
                        .getPersistenceManager()
                        .getConnection()
        ) {
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, wordId);
            st.setInt(2, usernameId);
            st.setBoolean(3, winner);
            int rowsAffected = st.executeUpdate();

            st = conn.prepareStatement(query1);
            st.setInt(1, usernameId);
            int rowsAffected1 = st.executeUpdate();


            if (rowsAffected > 0 && rowsAffected1 > 0) {
                result = responseJsonGame("Save-game", true, "");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
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

    private int getWordId(String word){
        int id = -1;

        String query = "SELECT id FROM \"HangMan\".public.\"Words\" WHERE text = ?";
        try(
                Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
        ) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, word);

            ResultSet rs = st.executeQuery();

            if (rs.next()) id = rs.getInt("id");

            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    private JsonObject responseJsonGame(String operation, boolean status, String errorMessage) {
        JsonObject result = new JsonObject();
        result.addProperty("operation", operation);
        result.addProperty("status", status);
        result.addProperty("errorMessage", errorMessage);
        return result;
    }

    public JsonObject errorJsonWord(){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "game");
        result.addProperty("status", false);
        result.addProperty("errorMessage", "Error");
        return result;
    }
}
