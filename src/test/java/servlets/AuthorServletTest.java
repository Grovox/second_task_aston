package servlets;

import dto.AuthorDelete;
import dto.AuthorId;
import dto.AuthorPost;
import model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.AuthorService;
import services.BookService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class AuthorServletTest {
    AuthorServlet servlet;
    AuthorService authorService;
    HttpServletRequest request;
    HttpServletResponse response;
    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new AuthorServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authorService = mock(AuthorService.class);

        Field field = AuthorServlet.class.getDeclaredField("authorService");
        field.setAccessible(true);
        field.set(servlet, authorService);
    }

    @Test
    void doPut() throws IOException{
        String jsonRequest = "{\n" +
                "        \"authorId\": 1,\n" +
                "        \"name\": \"jack D. V.\"\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(authorService.haveAuthorById(anyInt())).thenReturn(true);

        servlet.doPut(request, response);

        verify(authorService).changeAuthor(any(Author.class));
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
                "        \"authorId\": 1,\n" +
                "        \"name\": \"jack D. V.\"\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(authorService.haveAuthorById(anyInt())).thenReturn(false);

        servlet.doPut(request, response);

        verify(response).sendError(412);
    }

    @Test
    void doPost() throws IOException {
        String jsonRequest = "{\n" +
                "        \"name\": \"jack D. V.\"\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        servlet.doPost(request, response);

        verify(authorService).addAuthor(any(AuthorPost.class));
    }

    @Test
    void doPostBadRequest400() throws IOException {
        String jsonRequest = "";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        servlet.doPost(request, response);

        verify(response).sendError(400);
    }

    @Test
    void doDelete() throws IOException {
        String jsonRequest = "{\n" +
                "        \"authorId\": 1\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(authorService.haveAuthorById(anyInt())).thenReturn(true);

        servlet.doDelete(request, response);

        verify(authorService).deleteAuthor(any(AuthorDelete.class));
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
                "        \"authorId\": 1\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(authorService.haveAuthorById(anyInt())).thenReturn(false);

        servlet.doDelete(request, response);

        verify(response).sendError(412);
    }
}