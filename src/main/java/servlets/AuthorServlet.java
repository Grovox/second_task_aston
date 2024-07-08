package servlets;

import dto.AuthorDelete;
import dto.AuthorPost;
import mapper.AuthorMapper;
import model.Author;
import services.AuthorService;
import validators.JsonValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class AuthorServlet extends HttpServlet {

    private AuthorService authorService;

    @Override
    public void init() {
        authorService = AuthorService.getInstance();
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

        if (JsonValidator.validJsonToClass(json, Author.class)) {
            Author author = AuthorMapper.stringJsonToAuthor(json);
            if (authorService.haveAuthorById(author.getAuthorId())) {
                authorService.changeAuthor(author);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, AuthorPost.class)) {
            AuthorPost authorPost = AuthorMapper.stringJsonToAuthorPost(json);
            authorService.addAuthor(authorPost);
        } else resp.sendError(400);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, AuthorDelete.class)) {
            AuthorDelete authorDelete = AuthorMapper.stringJsonToAuthorDelete(json);
            if (authorService.haveAuthorById(authorDelete.getAuthorId())) {
                authorService.deleteAuthor(authorDelete);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
