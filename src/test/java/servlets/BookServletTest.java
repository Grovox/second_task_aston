package servlets;

import dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.AuthorService;
import services.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BookServletTest {
    BookServlet servlet;
    BookService bookService;
    AuthorService authorService;
    HttpServletRequest request;
    HttpServletResponse response;
    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new BookServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        bookService = mock(BookService.class);
        authorService = mock(AuthorService.class);

        Field field = BookServlet.class.getDeclaredField("bookService");
        field.setAccessible(true);
        field.set(servlet, bookService);

        field = BookServlet.class.getDeclaredField("authorService");
        field.setAccessible(true);
        field.set(servlet, authorService);
    }

    @Test
    void doPut() throws IOException {
        String jsonRequest = "{\n" +
                "        \"bookId\": 1,\n" +
                "        \"title\": \"qwerty\",\n" +
                "        \"price\": 1.1,\n" +
                "        \"amount\": 1,\n" +
                "        \"authorsId\": [\n" +
                "            {\n" +
                "        \"authorId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"authorId\": 2\n" +
                "    }\n" +
                "        ]\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(bookService.haveBookById(anyInt())).thenReturn(true);
        when(authorService.haveAuthorsById(any())).thenReturn(true);

        servlet.doPut(request, response);

        verify(bookService).changeBook(any(BookPut.class));
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
                "        \"bookId\": 1,\n" +
                "        \"title\": \"qwerty\",\n" +
                "        \"price\": 1.1,\n" +
                "        \"amount\": 1,\n" +
                "        \"authorsId\": [\n" +
                "            {\n" +
                "        \"authorId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"authorId\": 2\n" +
                "    }\n" +
                "        ]\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(bookService.haveBookById(anyInt())).thenReturn(false);
        when(authorService.haveAuthorsById(any())).thenReturn(false);

        servlet.doPut(request, response);

        verify(response).sendError(412);
    }

    @Test
    void doPost() throws IOException {
        String jsonRequest = "{\n" +
                "        \"title\": \"qwerty\",\n" +
                "        \"price\": 1.1,\n" +
                "        \"amount\": 1,\n" +
                "        \"authorsId\": [\n" +
                "            {\n" +
                "        \"authorId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"authorId\": 2\n" +
                "    }\n" +
                "        ]\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(authorService.haveAuthorsById(any())).thenReturn(true);

        servlet.doPost(request, response);

        verify(bookService).addBook(any(BookPost.class));
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
                "        \"title\": \"qwerty\",\n" +
                "        \"price\": 1.1,\n" +
                "        \"amount\": 1,\n" +
                "        \"authorsId\": [\n" +
                "            {\n" +
                "        \"authorId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"authorId\": 2\n" +
                "    }\n" +
                "        ]\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(authorService.haveAuthorsById(any())).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).sendError(412);
    }

    @Test
    void doDelete() throws IOException {
        String jsonRequest = "{\n" +
                "        \"bookId\": 1\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(bookService.haveBookById(anyInt())).thenReturn(true);
        when(authorService.haveAuthorsById(any())).thenReturn(true);

        servlet.doDelete(request, response);

        verify(bookService).deleteBook(any(BookDelete.class));
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
                "        \"bookId\": 1\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(bookService.haveBookById(anyInt())).thenReturn(false);
        when(authorService.haveAuthorsById(any())).thenReturn(false);

        servlet.doDelete(request, response);

        verify(response).sendError(412);
    }
}