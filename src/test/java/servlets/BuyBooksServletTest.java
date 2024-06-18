package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.BuyBookService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BuyBooksServletTest {
    BuyBooksServlet servlet;
    BuyBookService buyBookService;
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new BuyBooksServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        buyBookService = mock(BuyBookService.class);

        Field field = BuyBooksServlet.class.getDeclaredField("buyBookService");
        field.setAccessible(true);
        field.set(servlet, buyBookService);
    }

    @Test
    void doGet() throws IOException {
        when(buyBookService.getAllBuyBooks()).thenReturn(new ArrayList<>());
        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(buyBookService).getAllBuyBooks();
        verify(printWriter).print(anyString());
    }
}