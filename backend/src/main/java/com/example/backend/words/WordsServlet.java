package com.example.backend.words;

import com.example.backend.admin.AdminService;
import com.example.backend.token.TokenStorage;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "Words_Servlet" ,
        urlPatterns = {WordsService.WORDS_PATH}
)
public class WordsServlet extends HttpServlet {
    public void init(){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("authorization");
        System.out.println("Token = " + token);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject result = new JsonObject();
        String username = TokenStorage.getUsernameFromToken(token);

        WordsService ws = new WordsService();
        System.out.println("[DEBUG WORDS SERVLET] -> status utente: " + username);

        if(username != null) result = ws.getWord();
        else result = ws.errorJsonWord();

        out.println(result);
    }


    public void destroy(){

    }
}
