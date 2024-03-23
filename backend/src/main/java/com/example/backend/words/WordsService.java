package com.example.backend.words;

import com.example.backend.admin.DataStats;
import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WordsService {
    public static final String WORDS_PATH = "/word";

    public JsonObject getWord(){
        JsonObject res = new JsonObject();
        String tmp = null;
        ArrayList<String> words = new ArrayList<>();

        String query = "SELECT text FROM \"HangMan\".public.\"Words\"";
        try(
                Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
        ) {
            PreparedStatement st = conn.prepareStatement(query);
            Gson gson = new Gson();

            ResultSet rs = st.executeQuery();

            while (rs.next()) words.add(rs.getString(1));

            int randomIndex = (int) (Math.random() * words.size());

            res = responseJsonWord("stats", true, "", words.get(randomIndex));

            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            res = errorJsonWord();
        }

        return res;
    }

    private JsonObject responseJsonWord(
            String operation,
            boolean status,
            String errorMessage,
            String word
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("operation", operation);
        result.addProperty("status", status);
        result.addProperty("errorMessage", errorMessage);
        result.addProperty("word", word);
        return result;
    }

    public JsonObject errorJsonWord(){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "stats");
        result.addProperty("status", false);
        result.addProperty("errorMessage", "Error");
        return result;
    }




}
