package com.example.backend.admin;

import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminService {
    public static final String STATS_PATH = "/stats";

    public JsonObject getStats(){
        JsonObject res = new JsonObject();
        DataStats tmp = null;
        ArrayList<String> data = new ArrayList<>();

        String query = "SELECT \"Users\".id, username, wins, loses FROM \"HangMan\".public.\"Users\", \"Stats\" WHERE \"Users\".id = \"Stats\".id ORDER BY wins DESC";
        try(
                Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
        ) {
            PreparedStatement st = conn.prepareStatement(query);
            Gson gson = new Gson();

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tmp = new DataStats(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                data.add(gson.toJson(tmp));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            res = errorJsonStats();
        }

        res = responseJsonStats("stats", true, "", data);

        return res;
    }

    private JsonObject responseJsonStats(
            String operation,
            boolean status,
            String errorMessage,
            ArrayList<String> data
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("operation", operation);
        result.addProperty("status", status);
        result.addProperty("errorMessage", errorMessage);
        result.addProperty("data", String.valueOf(data));
        return result;
    }

    public JsonObject errorJsonStats(){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "stats");
        result.addProperty("status", false);
        result.addProperty("errorMessage", "Not allowed");
        return result;
    }




}
