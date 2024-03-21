package com.example.backend.token;

import com.example.backend.token.TokenStorage;
import jakarta.servlet.http.HttpSession;

public class TokenService {

  private static final String SESSION_USER_KEY = "user";
  private static final String SESSION_PRIVILEGE_KEY = "privilege";
  public static final String LOGIN_PATH = "/login";
  public static final String LOGOUT_PATH = "/logout";

  /* Restituisce lo username dell'utente che ha fatto log in in questa sessione
   * o la stringa vuota se un utente siffatto non esiste
   */
  public static String getCurrentLogin(HttpSession session) {
    if (session.getAttribute(SESSION_USER_KEY) == null) return "";
    return (String) session.getAttribute(SESSION_USER_KEY);
  }

  public static int getCurrentPrivilege(String username) {
    return TokenStorage.getPrivilegi(username);
  }

  public static boolean doLogOut(String username) {
    return TokenStorage.removeToken(username);
  }
}
