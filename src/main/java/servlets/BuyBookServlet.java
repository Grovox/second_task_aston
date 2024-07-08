package servlets;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import mapper.BuyBookMapper;
import model.BuyBook;
import services.BookService;
import services.BuyBookService;
import services.UserService;
import validators.JsonValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class BuyBookServlet extends HttpServlet {

    private BuyBookService buyBookService;
    private UserService userService;
    private BookService bookService;

    @Override
    public void init() {
        buyBookService = BuyBookService.getInstance();
        userService = UserService.getInstance();
        bookService = BookService.getInstance();
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

        if (JsonValidator.validJsonToClass(json, BuyBook.class)) {
            BuyBook buyBook = BuyBookMapper.stringJsonToBuyBook(json);
            if (buyBookService.haveBuyBookById(buyBook.getBuyBookId()) &&
                    userService.haveUserById(buyBook.getUserId()) &&
                    bookService.haveBookById(buyBook.getBookId()) &&
                    bookService.isEnoughBooks(buyBook)) {
                buyBookService.changeBuyBook(buyBook);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, BuyBookPost.class)) {
            BuyBookPost buyBookPost = BuyBookMapper.stringJsonToBuyBookPost(json);
            if (userService.haveUserById(buyBookPost.getUserId()) &&
                    bookService.haveBookById(buyBookPost.getBookId()) &&
                    bookService.isEnoughBooks(buyBookPost)) {
                buyBookService.addBuyBook(buyBookPost);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);

        if (JsonValidator.validJsonToClass(json, BuyBookDelete.class)) {
            BuyBookDelete buyBook = BuyBookMapper.stringJsonToBuyBookDelete(json);
            if (buyBookService.haveBuyBookById(buyBook.getBuyBookId())) {
                buyBookService.deleteBuyBook(buyBook);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
