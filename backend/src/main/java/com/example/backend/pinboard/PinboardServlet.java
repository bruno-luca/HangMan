package com.example.backend.pinboard;

import com.example.backend.token.TokenStorage;
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
        name = "Pinboard_Servlet" ,
        urlPatterns = {PinboardService.PINBOARD_PATH}
)
public class PinboardServlet extends HttpServlet {
    public void init(){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("authorization");
        System.out.println("Token = " + token);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject result = new JsonObject();
        String username = TokenStorage.getUsernameFromToken(token);
        int privilege = TokenStorage.getPrivilegi(username);

        PinboardService ps = new PinboardService();
        System.out.println("[DEBUG PINBOARD SERVLET] -> status utente: " + username);

        if(username != null) result = ps.getMessages();
        else result = ps.errorJsonMessage("get-posts", "Permission denied");

        out.println(result);
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
        String content= registerObject.get("content").getAsString();
        System.out.println(content);

        PrintWriter out = response.getWriter();
        JsonObject result = new JsonObject();
        String username = TokenStorage.getUsernameFromToken(token);
        int privilege = TokenStorage.getPrivilegi(username);

        PinboardService ps = new PinboardService();
        System.out.println("[DEBUG PINBOARD SERVLET] -> status utente=" + username);

        if(privilege == 1) result = ps.addMessage(content, username);
        else result = ps.errorJsonMessage("add-post" ,"Permission denied");

        out.println(result);
    }

    public void destroy(){

    }
}
