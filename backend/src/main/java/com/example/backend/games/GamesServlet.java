package com.example.backend.games;

import com.example.backend.token.TokenStorage;
import com.example.backend.words.WordsService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "Games_Servlet" ,
        urlPatterns = {GamesService.GAMES_PATH}
)
public class GamesServlet extends HttpServlet {
    public void init(){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        BufferedReader in = request.getReader();
        JsonObject registerObject = JsonParser.parseReader(in).getAsJsonObject();
        registerObject =
                JsonParser
                        .parseString(registerObject.get("body").getAsString())
                        .getAsJsonObject();
        String token = registerObject.get("token").getAsString();
        System.out.println(token);
        String word = registerObject.get("word").getAsString();
        System.out.println(word);
        int gameResult = registerObject.get("gameResult").getAsInt();
        System.out.println(gameResult);

        PrintWriter out = response.getWriter();
        JsonObject result = new JsonObject();
        String username = TokenStorage.getUsernameFromToken(token);

        GamesService gs = new GamesService();
        System.out.println("[DEBUG GAMES SERVLET] -> status utente=" + username + " parola=" + word + " esito: " +((gameResult == 1) ? "vince": "perde"));

        if(username != null) result = gs.saveGame(username, word, gameResult == 1);
        else result = gs.errorJsonWord();

        out.println(result);
    }

    public void destroy(){

    }
}
