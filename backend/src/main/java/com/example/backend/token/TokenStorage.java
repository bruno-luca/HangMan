package com.example.backend.token;

import java.util.HashMap;
import java.util.Map;

public class TokenStorage {

  private static final Map<String, TokenInfo> userTokens = new HashMap<>();

  public static void addToken(String username, String token, int privilegi) {
    TokenInfo tokenInfo = new TokenInfo(token, privilegi);
    userTokens.put(username, tokenInfo);
  }
  public static boolean isUserActive(String username) {
    return userTokens.containsKey(username);
  }

  public static boolean removeToken(String username) {
    if (userTokens.containsKey(username)) {
      userTokens.remove(username);
      return true; // Il logout è avvenuto con successo
    } else {
      return false; // Il token non è presente, logout fallito
    }
  }

  public static boolean isValidToken(String username, String token) {
    token = token.replace("Bearer $", "");
    return (
      userTokens.containsKey(username) &&
      userTokens.get(username).getToken().equals(token)
    );
  }

  public static int getPrivilegi(String username) {
    if (userTokens.containsKey(username)) {
      return userTokens.get(username).getPrivilegi();
    } else {
      return -1; // Valore di default o segnalazione di errore, a seconda delle esigenze
    }
  }



  public static String getUsernameFromToken(String token) {
    token = token.replace("Bearer $", "");
    // Itera sulla mappa per trovare l'username associato al token
    for (Map.Entry<String, TokenInfo> entry : userTokens.entrySet()) {
      if (entry.getValue().getToken().equals(token)) {
        return entry.getKey();
      }
    }

    // Restituisci null se il token non è associato a un username
    return null;
  }

  public static void printTokenMap() {
    System.out.println("Contenuto della mappa dei token:");
    for (Map.Entry<String, TokenInfo> entry : userTokens.entrySet()) {
      System.out.println(
        "Utente: " +
        entry.getKey() +
        ", Token: " +
        entry.getValue().getToken() +
        ", Privilegi: " +
        entry.getValue().getPrivilegi()
      );
    }
  }

  private static class TokenInfo {

    private final String token;
    private final int privilegi;

    public TokenInfo(String token, int privilegi) {
      this.token = token;
      this.privilegi = privilegi;
    }

    public String getToken() {
      return token;
    }

    public int getPrivilegi() {
      return privilegi;
    }
  }
}
