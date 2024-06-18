package servlets;

import convectors.UserConvector;
import dto.UserDelete;
import dto.UserPost;
import model.User;
import services.UserService;
import validators.JsonValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = UserService.getInstance();
    }

    private String getJsonFromRequest(HttpServletRequest req) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        BufferedReader reader;
        reader = req.getReader();
        while ((line = reader.readLine()) != null)
            jsonBuilder.append(line);
        return jsonBuilder.toString();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, User.class)) {
            User user = UserConvector.stringJsonToUser(json);
            if (userService.haveUserById(user.getUserId())) {
                userService.changeUser(user);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, UserPost.class)) {
            UserPost userPost = UserConvector.stringJsonToUserPost(json);
            userService.addUser(userPost);
        } else resp.sendError(400);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, UserDelete.class)) {
            UserDelete userDelete = UserConvector.stringJsonToUserDelete(json);
            if (userService.haveUserById(userDelete.getUserId())) {
                userService.deleteUser(userDelete);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
