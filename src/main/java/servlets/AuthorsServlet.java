package servlets;

import mapper.AuthorMapper;
import services.AuthorService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorsServlet extends HttpServlet {

    private AuthorService authorService;

    @Override
    public void init() {
        authorService = AuthorService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String json = AuthorMapper.allAuthorsToJsonString(authorService.getAllAuthors());
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
