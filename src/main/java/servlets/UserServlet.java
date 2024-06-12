package servlets;

import convectors.UserConvector;
import dto.UserPost;
import model.User;
import services.UserService;
import validators.JsonValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        String stringJson = json.toString();
        if (JsonValidator.validJsonToClass(stringJson, User.class)) {
            User user = UserConvector.stringJsonToUser(stringJson);
            if (UserService.haveUserById(user.getUserId())) {
                UserService.changeUser(user);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        String stringJson = json.toString();
        if (JsonValidator.validJsonToClass(stringJson, UserPost.class)) {
            UserPost userPost = UserConvector.stringJsonToUserPost(stringJson);
            UserService.addUser(userPost);
        } else resp.sendError(400);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        String stringJson = json.toString();
        if (JsonValidator.validJsonToClass(stringJson, User.class)) {
            User user = UserConvector.stringJsonToUser(stringJson);
            if (UserService.haveUser(user)) {
                UserService.deleteUser(user);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
