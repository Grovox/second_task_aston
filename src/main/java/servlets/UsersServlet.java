package servlets;

import mapper.UserMapper;
import services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String json = UserMapper.allUsersToJsonString(userService.getAllUsers());
        PrintWriter out;

        out = resp.getWriter();

        out.print(json);
        out.flush();
    }
}
