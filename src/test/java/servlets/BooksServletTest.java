package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BooksServletTest {
    BooksServlet servlet;
    BookService bookService;
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new BooksServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        bookService = mock(BookService.class);

        Field field = BooksServlet.class.getDeclaredField("bookService");
        field.setAccessible(true);
        field.set(servlet, bookService);
    }

    @Test
    void doGet() throws IOException {
        when(bookService.getAllBook()).thenReturn(new ArrayList<>());
        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(bookService).getAllBook();
        verify(printWriter).print(anyString());
    }

}