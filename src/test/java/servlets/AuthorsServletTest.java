package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.AuthorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class AuthorsServletTest {
    AuthorsServlet servlet;
    AuthorService authorService;
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new AuthorsServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authorService = mock(AuthorService.class);

        Field field = AuthorsServlet.class.getDeclaredField("authorService");
        field.setAccessible(true);
        field.set(servlet, authorService);
    }

    @Test
    void doGet() throws IOException {
        when(authorService.getAllAuthors()).thenReturn(new ArrayList<>());
        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(authorService).getAllAuthors();
        verify(printWriter).print(anyString());
    }
}