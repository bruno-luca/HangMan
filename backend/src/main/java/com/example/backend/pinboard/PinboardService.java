package com.example.backend.pinboard;

import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PinboardService {
    public static final String PINBOARD_PATH = "/pinboard";

    public JsonObject getMessages(){
        JsonObject res = new JsonObject();
        Message tmp = null;
        ArrayList<String> data = new ArrayList<>();

        String query = "SELECT username, date, time, content FROM \"HangMan\".public.\"Users\", \"Posts\" WHERE \"Users\".id = \"userId\" ORDER BY date DESC, time DESC";
        try(
                Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
        ) {
            PreparedStatement st = conn.prepareStatement(query);
            Gson gson = new Gson();

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tmp = new Message(rs.getString(1), rs.getDate(2).toString(), rs.getTime(3).toString(), rs.getString(4));
                data.add(gson.toJson(tmp));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            res = errorJsonMessage("get-posts","Error while retriving data from DB");
        }

        res = responseJsonMessage(data);

        return res;
    }

    public JsonObject addMessage(String content, String username){
        int usernameId = getUsernameId(username);

        JsonObject result = null;

        String query = "INSERT INTO  \"HangMan\".public.\"Posts\" (\"userId\", date, time, content) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = PoolingPersistenceManager
                        .getPersistenceManager()
                        .getConnection()
        ) {
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, usernameId);
            st.setDate(2, Date.valueOf(LocalDate.now()));
            st.setTime(3, Time.valueOf(LocalTime.now()));
            st.setString(4, content);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0 ) result = responseJsonMessage("add-post", true);
            else result = errorJsonMessage("add-post", "Error while saving post on DB");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = errorJsonMessage("add-post", "Error while saving post on DB");
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
    private JsonObject responseJsonMessage(ArrayList data){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "message");
        result.addProperty("status", true);
        result.addProperty("data", String.valueOf(data));
        return result;
    }

    private JsonObject responseJsonMessage(String operation, boolean success){
        JsonObject result = new JsonObject();
        result.addProperty("operation", operation);
        result.addProperty("status", success);
        return result;
    }

    public JsonObject errorJsonMessage(){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "message");
        result.addProperty("status", "false");
        result.addProperty("errorMessage", "General error");
        return result;
    }

    public JsonObject errorJsonMessage(String operation, String errorMessage){
        JsonObject result = new JsonObject();
        result.addProperty("operation", operation);
        result.addProperty("status", "false");
        result.addProperty("errorMessage", errorMessage);
        return result;
    }
}
