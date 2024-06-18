package servlets;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.BookService;
import services.BuyBookService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BuyBookServletTest {
    BuyBookServlet servlet;
    BuyBookService buyBookService;
    UserService userService;
    BookService bookService;
    HttpServletRequest request;
    HttpServletResponse response;
    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new BuyBookServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        buyBookService = mock(BuyBookService.class);
        userService = mock(UserService.class);
        bookService = mock(BookService.class);

        Field field = BuyBookServlet.class.getDeclaredField("buyBookService");
        field.setAccessible(true);
        field.set(servlet, buyBookService);

        field = BuyBookServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userService);

        field = BuyBookServlet.class.getDeclaredField("bookService");
        field.setAccessible(true);
        field.set(servlet, bookService);
    }

    @Test
    void doPut() throws IOException {
        String jsonRequest = "{\n" +
                "    \"buyBookId\" : 1,\n" +
                "    \"userId\" : 1,\n" +
                "    \"bookId\" : 1,\n" +
                "    \"amount\" : 5\n" +
                "}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(buyBookService.haveBuyBookById(anyInt())).thenReturn(true);
        when(userService.haveUserById(anyInt())).thenReturn(true);
        when(bookService.haveBookById(anyInt())).thenReturn(true);
        when(bookService.isEnoughBooks(any(BuyBook.class))).thenReturn(true);

        servlet.doPut(request, response);

        verify(buyBookService).changeBuyBook(any(BuyBook.class));
    }

    @Test
    void doPutBadRequest400() throws IOException {
        String jsonRequest = "";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        servlet.doPut(request, response);

        verify(response).sendError(400);
    }

    @Test
    void doPutBadRequest412() throws IOException{
        String jsonRequest = "{\n" +
                "    \"buyBookId\" : 1,\n" +
                "    \"userId\" : 1,\n" +
                "    \"bookId\" : 1,\n" +
                "    \"amount\" : 5\n" +
                "}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(buyBookService.haveBuyBookById(anyInt())).thenReturn(false);
        when(userService.haveUserById(anyInt())).thenReturn(false);
        when(bookService.haveBookById(anyInt())).thenReturn(false);
        when(bookService.isEnoughBooks(any(BuyBook.class))).thenReturn(false);

        servlet.doPut(request, response);

        verify(response).sendError(412);
    }

    @Test
    void doPost() throws IOException {
        String jsonRequest = "{\n" +
                "    \"userId\" : 1,\n" +
                "    \"bookId\" : 1,\n" +
                "    \"amount\" : 5\n" +
                "}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(userService.haveUserById(anyInt())).thenReturn(true);
        when(bookService.haveBookById(anyInt())).thenReturn(true);
        when(bookService.isEnoughBooks(any(BuyBookPost.class))).thenReturn(true);

        servlet.doPost(request, response);

        verify(buyBookService).addBuyBook(any(BuyBookPost.class));
    }

    @Test
    void doPostBadRequest400() throws IOException {
        String jsonRequest = "";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        servlet.doPost(request, response);

        verify(response).sendError(400);
    }

    @Test
    void doPostBadRequest412() throws IOException {
        String jsonRequest = "{\n" +
                "    \"userId\" : 1,\n" +
                "    \"bookId\" : 1,\n" +
                "    \"amount\" : 5\n" +
                "}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(userService.haveUserById(anyInt())).thenReturn(false);
        when(bookService.haveBookById(anyInt())).thenReturn(false);
        when(bookService.isEnoughBooks(any(BuyBook.class))).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).sendError(412);
    }

    @Test
    void doDelete() throws IOException {
        String jsonRequest = "{\n" +
                "    \"buyBookId\" : 1\n" +
                "}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(buyBookService.haveBuyBookById(anyInt())).thenReturn(true);

        servlet.doDelete(request, response);

        verify(buyBookService).deleteBuyBook(any(BuyBookDelete.class));
    }
    @Test
    void doDeleteBadRequest400() throws IOException{
        String jsonRequest = "";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        servlet.doDelete(request, response);

        verify(response).sendError(400);
    }
    @Test
    void doDeleteBadRequest412() throws IOException {
        String jsonRequest = "{\n" +
                "    \"buyBookId\" : 1\n" +
                "}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(buyBookService.haveBuyBookById(anyInt())).thenReturn(false);

        servlet.doDelete(request, response);

        verify(response).sendError(412);
    }
}