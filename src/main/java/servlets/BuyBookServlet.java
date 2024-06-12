package servlets;

import convectors.BuyBookConvector;
import dto.BuyBookPost;
import model.BuyBook;
import services.BookService;
import services.BuyBookService;
import services.UserService;
import validators.JsonValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class BuyBookServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        String stringJson = json.toString();
        if (JsonValidator.validJsonToClass(stringJson, BuyBook.class)) {
            BuyBook buyBook = BuyBookConvector.stringJsonToBuyBook(stringJson);
            if (BuyBookService.haveBuyBookById(buyBook.getBuyBookId()) &&
                    UserService.haveUserById(buyBook.getUserId()) &&
                    BookService.haveBookById(buyBook.getBookId()) &&
                    BuyBookService.isEnoughBooks(buyBook.getBookId(), buyBook.getAmount())) {
                BuyBookService.changeBuyBook(buyBook);
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
        if (JsonValidator.validJsonToClass(stringJson, BuyBookPost.class)) {
            BuyBookPost buyBookPost = BuyBookConvector.stringJsonToBuyBookPost(stringJson);
            if (UserService.haveUserById(buyBookPost.getUserId()) &&
                    BookService.haveBookById(buyBookPost.getBookId()) &&
                    BuyBookService.isEnoughBooks(buyBookPost.getBookId(), buyBookPost.getAmount())) {
                BuyBookService.addBuyBook(buyBookPost);
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
        if (JsonValidator.validJsonToClass(stringJson, BuyBook.class)) {
            BuyBook buyBook = BuyBookConvector.stringJsonToBuyBook(stringJson);
            if (BuyBookService.haveBuyBook(buyBook)) {
                BuyBookService.deleteBuyBook(buyBook);
            } else resp.sendError(412);
        } else resp.sendError(400);
    }
}
