package servlets;

import convectors.BookConvector;
import services.BookService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BooksServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() {
        bookService = BookService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String json = BookConvector.allBookGetToJsonString(bookService.getAllBook());
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
