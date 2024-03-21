package com.example.backend.admin;

import com.google.gson.Gson;

public class DataStats {

    private int id;
    private String username;
    private int wins;
    private int loses;

    public DataStats(int id, String username, int wins, int loses) {
        this.id = id;
        this.username = username;
        this.wins = wins;
        this.loses = loses;
    }
}
