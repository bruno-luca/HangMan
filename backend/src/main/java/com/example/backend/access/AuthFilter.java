package com.example.backend.access;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.games.GamesService;
import com.example.backend.token.JwtUtil;
import com.example.backend.token.TokenService;
import com.example.backend.token.TokenStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pinboard.PinboardService;
import pinboard.PinboardServlet;

import java.io.IOException;

@WebFilter(urlPatterns = "*")
public class AuthFilter extends HttpFilter {

  @Override
  public void doFilter(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    String origin = request.getHeader("Origin");


    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader(
      "Access-Control-Allow-Methods",
      "GET, OPTIONS, HEAD, PUT, POST, PATCH, DELETE"
    );
    response.setHeader("Access-Control-Allow-Headers", "*, Authorization");
    response.setHeader("Access-Control-Allow-Credentials", "true");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    if (request.getServletPath().equals(TokenService.LOGIN_PATH) ||
            request.getServletPath().equals(TokenService.LOGOUT_PATH) ||
            request.getServletPath().equals(RegisterService.REGISTER_PATH) ||
            request.getServletPath().equals(GamesService.GAMES_PATH) ||
            request.getServletPath().equals(PinboardService.PINBOARD_PATH)) {
      chain.doFilter(request, response);

      return;
    }

    String token = request.getHeader("authorization");

    //TokenStorage.printTokenMap();

    if (token != null && token.startsWith("Bearer ")) {
      try {
        token = token.replace("Bearer $", "");
        DecodedJWT decodedJWT = JwtUtil.verifyToken(token);
        String username = decodedJWT.getClaim("username").asString();
        //Verifica se il token è valido nella tua struttura dati
        if (TokenStorage.isValidToken(username, token)) {
          //System.out.println("[DEBUG AUTH FILTER] -> utente autorizzato: " + username);
          // L'utente è autenticato, procedi con la richiesta
          chain.doFilter(request, response);
        } else {
          // Token non valido
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Token non valido");
        }
      } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token non valido");
      }
    } else {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Non autorizzato");
    }
  }
}
