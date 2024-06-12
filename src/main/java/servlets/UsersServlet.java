package servlets;

import convectors.UserConvector;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = UserConvector.allUsersToJsonString(UserService.getAllUsers());
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
