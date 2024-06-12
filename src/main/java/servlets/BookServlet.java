package servlets;

import convectors.BookConvector;
import dto.BookPost;
import model.Book;
import services.AuthorService;
import services.BookService;
import validators.JsonValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class BookServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        String stringJson = json.toString();
        if (JsonValidator.validJsonToClass(stringJson, Book.class)) {
            Book book = BookConvector.stringJsonToBook(stringJson);

            if (BookService.haveBookById(book.getBookId()) &&
                    AuthorService.haveAuthors(book.getAuthors())) {
                BookService.changeBook(book);
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
        if (JsonValidator.validJsonToClass(stringJson, BookPost.class)) {
            BookPost bookPost = BookConvector.stringJsonToBookPost(stringJson);
            if (AuthorService.haveAuthors(bookPost.getAuthors())) {
                BookService.addBook(bookPost);
            } else resp.sendError(412);
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
        if (JsonValidator.validJsonToClass(stringJson, Book.class)) {
            Book book = BookConvector.stringJsonToBook(stringJson);
            if (BookService.haveBook(book)) {
                BookService.deleteBook(book);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
