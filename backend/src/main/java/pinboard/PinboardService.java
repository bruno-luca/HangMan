package pinboard;

import com.example.backend.admin.DataStats;
import com.example.backend.db.PoolingPersistenceManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.json.Json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PinboardService {
    public static final String PINBOARD_PATH = "/pinboard";

    public JsonObject getMessages(){
        JsonObject res = new JsonObject();
        Message tmp = null;
        ArrayList<String> data = new ArrayList<>();

        String query = "SELECT username, date, time, content FROM \"HangMan\".public.\"Users\", \"Posts\" WHERE \"Users\".id = \"userId\" ORDER BY date DESC, time DESC";
        try(
                Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
        ) {
            PreparedStatement st = conn.prepareStatement(query);
            Gson gson = new Gson();

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tmp = new Message(rs.getString(1), rs.getDate(2).toString(), rs.getTime(3).toString(), rs.getString(4));
                data.add(gson.toJson(tmp));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            res = errorJsonMessage("Error while retriving data from DB");
        }

        res = responseJsonMessage(data);

        return res;
    }

    public JsonObject addMessage(){
        return null;
    }

    private JsonObject responseJsonMessage(ArrayList data){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "message");
        result.addProperty("status", true);
        result.addProperty("data", String.valueOf(data));
        return result;
    }

    public JsonObject errorJsonMessage(){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "message");
        result.addProperty("status", "false");
        result.addProperty("errorMessage", "General error");
        return result;
    }

    public JsonObject errorJsonMessage(String errorMessage){
        JsonObject result = new JsonObject();
        result.addProperty("operation", "message");
        result.addProperty("status", "false");
        result.addProperty("errorMessage", errorMessage);
        return result;
    }
}
