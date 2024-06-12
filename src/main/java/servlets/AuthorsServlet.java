package servlets;

import convectors.AuthorConvector;
import services.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = AuthorConvector.allAuthorsToJsonString(AuthorService.getAllAuthors());
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
