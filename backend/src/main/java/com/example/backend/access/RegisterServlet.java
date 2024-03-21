package com.example.backend.access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.*;

@WebServlet(
  name = "Signup_Servlet",
  urlPatterns = { RegisterService.REGISTER_PATH }
)
public class RegisterServlet extends HttpServlet {

  public void init() {}

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    RegisterService registerService = new RegisterService();
    response.setContentType("application/json");
    BufferedReader in = request.getReader();

    JsonObject registerObject = JsonParser.parseReader(in).getAsJsonObject();
    registerObject =
      JsonParser
        .parseString(registerObject.get("body").getAsString())
        .getAsJsonObject();
    String username = registerObject.get("username").getAsString();
    String password = registerObject.get("password").getAsString();

    if (request.getServletPath().equals(RegisterService.REGISTER_PATH)) {
      PrintWriter out = response.getWriter();
      JsonObject result = null;

      if ((username == null || username.isEmpty())) {
        System.out.println("[DEBUG REGISTER SERVLET]: tentativo di registrazione fallita, errore: l'utente esiste di già");
        result =
          registerService.responseJsonRegister(
            "register",
            false,
            "username non valida"
          );
        out.println(result);
        return;
      } else if (password == null || password.isEmpty()) {
        System.out.println("[DEBUG REGISTER SERVLET]: tentativo di registrazione fallita, errore: password non valida");
        result =
          registerService.responseJsonRegister(
            "register",
            false,
            "password non valida"
          );
        out.println(result);
        return;
      }

      if (!registerService.existUsername(username)) {
        System.out.println("[DEBUG LOG]: registrazione nuovo utente:"+username);
        registerService.RegisterUsername(username, password, 0);
        result = registerService.responseJsonRegister("register", true, "");
        out.println(result);
        return;

      }
      System.out.println("[DEBUG REGISTER SERVLET]: tentativo di registrazione fallita, errore: l'utente esiste di già");
      result =
        registerService.responseJsonRegister(
          "register",
          false,
          "l'utente esiste di già"
        );
      out.println(result);
    }
  }
}
