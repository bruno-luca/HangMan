package pinboard;

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
        else result = ps.errorJsonMessage("Permission denied");

        out.println(result);
    }

    public void destroy(){

    }
}
