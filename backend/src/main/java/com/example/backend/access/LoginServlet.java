package com.example.backend.access;

import com.example.backend.token.JwtUtil;
import com.example.backend.token.TokenService;
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
        name = "Login_Servlet" ,
        urlPatterns = {TokenService.LOGIN_PATH, TokenService.LOGOUT_PATH}
)
public class LoginServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String token = request.getHeader("authorization");
        System.out.println("Token = " + token);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject result = null;
        String username = TokenStorage.getUsernameFromToken(token);
        int privilege = TokenStorage.getPrivilegi(username);
        System.out.println("[DEBUG LOGIN SERVLET] -> status utente: "+username);

        if (request.getServletPath().equals(TokenService.LOGIN_PATH)) {
            if (!username.isEmpty()) {
                result = outputJsonBuild("status", privilege, true, "");
                result.addProperty("username", username);
                result.addProperty("token", token);
            } else {
                result = outputJsonBuild("status", -1, false, "No logged user");
            }
        } else if (request.getServletPath().equals(TokenService.LOGOUT_PATH)) {
            if (!username.isEmpty()) {
                System.out.println("[DEBUG LOGIN SERVLET] -> logout  utente: "+username);
                TokenService.doLogOut(username);
                result = outputJsonBuild("logout", privilege, true, "");
                result.addProperty("username", username);
            } else {
                result = outputJsonBuild("logout", -1, false, "No logged user");
            }
        }
        out.println(result);
    }

    private JsonObject outputJsonBuild(
            String operation,
            int privilege,
            boolean status,
            String errorMessage
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("operation", operation);
        result.addProperty("privilege", privilege);
        result.addProperty("status", status);
        result.addProperty("errorMessage", errorMessage);
        return result;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (request.getServletPath().equals(TokenService.LOGIN_PATH)) {
            response.setContentType("application/json");
            BufferedReader in = request.getReader();
            JsonObject loginObject = JsonParser.parseReader(in).getAsJsonObject();
            loginObject = JsonParser.parseString(loginObject.get("body").getAsString()).getAsJsonObject();
            String username = loginObject.get("username").getAsString();
            String password = loginObject.get("password").getAsString();

            JsonObject result = null;

            int valid = LoginManager
                    .getManager()
                    .validateCredentials(username, password);
            if (valid == 0 || valid == 1) {
                System.out.println("[DEBUG LOGIN SERVLET] -> login utente: "+username);
                String token = JwtUtil.generateToken(username);
                TokenStorage.addToken(username, token, valid);
                TokenStorage.printTokenMap();
                response.setStatus(HttpServletResponse.SC_OK);
                result = outputJsonBuild("login", valid, true, "");
                result.addProperty("token", token);
                result.addProperty("username", username);
                LoginManager.getManager().insertAccesso(username);
            } else {
                result = outputJsonBuild("login", valid, false, "Invalid credentials");
            }
            PrintWriter out = response.getWriter();
            out.println(result);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void destroy() {
    }
}
