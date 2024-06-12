package servlets;

import convectors.AuthorConvector;
import dto.AuthorPost;
import model.Author;
import services.AuthorService;
import validators.JsonValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class AuthorServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        String stringJson = json.toString();
        if (JsonValidator.validJsonToClass(stringJson, Author.class)){
            Author author = AuthorConvector.stringJsonToAuthor(stringJson);
            if (AuthorService.haveAuthorById(author.getAuthorId())) {
                AuthorService.changeAuthor(author);
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
        if (JsonValidator.validJsonToClass(stringJson, AuthorPost.class)){
            AuthorPost authorPost = AuthorConvector.stringJsonToAuthorPost(stringJson);
            AuthorService.addAuthor(authorPost);
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
        if (JsonValidator.validJsonToClass(stringJson, Author.class)){
            Author author = AuthorConvector.stringJsonToAuthor(stringJson);
            if (AuthorService.haveAuthor(author)) {
                AuthorService.deleteAuthor(author);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
