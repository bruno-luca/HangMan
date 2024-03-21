package com.example.backend.access;

import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.JsonObject;
import java.sql.*;

public class RegisterService {

  public static final String REGISTER_PATH = "/signup";

  JsonObject responseJsonRegister(
    String operation,
    boolean status,
    String errorMessage
  ) {
    JsonObject result = new JsonObject();
    result.addProperty("operation", operation);
    result.addProperty("status", status);
    result.addProperty("errorMessage", errorMessage);
    return result;
  }

  public boolean existUsername(String Username) {
    boolean state = false;

    String query =
      "SELECT COUNT(*) FROM \"HangMan\".public.\"Users\" WHERE username = ? ";

    try (
      Connection conn = PoolingPersistenceManager
        .getPersistenceManager()
        .getConnection()
    ) {
      PreparedStatement st = conn.prepareStatement(query);
      st.setString(1, Username);

      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        int rowCount = rs.getInt(1);
        state = rowCount > 0;
      }
      st.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return state;
  }

  public boolean RegisterUsername(
    String username,
    String password,
    int admin
  ) {
    boolean result = false;

    String query =
      "INSERT INTO \"HangMan\".public.\"Users\" (username,password,admin) VALUES (?,?,?)";
    try (
      Connection conn = PoolingPersistenceManager
        .getPersistenceManager()
        .getConnection()
    ) {
      PreparedStatement st = conn.prepareStatement(query);
      st.setString(1, username);
      st.setString(2, password);
      st.setInt(3, admin);

      int rowsAffected = st.executeUpdate();

      if (rowsAffected > 0) {

        result = true;
      }
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
