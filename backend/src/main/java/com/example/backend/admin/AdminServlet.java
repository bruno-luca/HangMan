package com.example.backend.admin;

import com.example.backend.token.TokenService;
import com.example.backend.token.TokenStorage;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(
        name = "Admin_Servlet" ,
        urlPatterns = {AdminService.STATS_PATH}
)

public class AdminServlet extends HttpServlet {
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

        AdminService as = new AdminService();
        System.out.println("[DEBUG ADMIN SERVLET] -> status utente: " + username);

        if(privilege == 1) result = as.getStats();
        else result = as.errorJsonStats();


        out.println(result);
    }


    public void destroy(){

    }


}
