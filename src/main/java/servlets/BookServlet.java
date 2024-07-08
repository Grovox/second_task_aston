package servlets;

import dto.BookDelete;
import dto.BookPost;
import dto.BookPut;
import mapper.BookMapper;
import services.AuthorService;
import services.BookService;
import validators.JsonValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class BookServlet extends HttpServlet {

    private BookService bookService;
    private AuthorService authorService;

    @Override
    public void init() {
        bookService = BookService.getInstance();
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

        if (JsonValidator.validJsonToClass(json, BookPut.class)) {
            BookPut bookPut = BookMapper.stringJsonToBookPut(json);
            if (bookService.haveBookById(bookPut.getBookId()) &&
                    authorService.haveAuthorsById(bookPut.getAuthorsId())) {
                bookService.changeBook(bookPut);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, BookPost.class)) {
            BookPost bookPost = BookMapper.stringJsonToBookPost(json);
            if (authorService.haveAuthorsById(bookPost.getAuthorsId())) {
                bookService.addBook(bookPost);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, BookDelete.class)) {
            BookDelete bookDelete = BookMapper.stringJsonToBookDelete(json);
            if (bookService.haveBookById(bookDelete.getBookId())) {
                bookService.deleteBook(bookDelete);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
