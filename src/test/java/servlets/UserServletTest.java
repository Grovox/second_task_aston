package servlets;

import dto.UserDelete;
import dto.UserPost;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class UserServletTest {
    UserServlet servlet;
    UserService userService;
    HttpServletRequest request;
    HttpServletResponse response;
    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new UserServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userService = mock(UserService.class);

        Field field = UserServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userService);
    }

    @Test
    void doPut() throws IOException {
        String jsonRequest = "{\n" +
                "        \"userId\": 1,\n" +
                "        \"name\": \"jack D. V.\"\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(userService.haveUserById(anyInt())).thenReturn(true);

        servlet.doPut(request, response);

        verify(userService).changeUser(any(User.class));

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
                "        \"userId\": 1,\n" +
                "        \"name\": \"jack D. V.\"\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(userService.haveUserById(anyInt())).thenReturn(false);

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

        verify(userService).addUser(any(UserPost.class));
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
                "        \"userId\": 1\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(userService.haveUserById(anyInt())).thenReturn(true);

        servlet.doDelete(request, response);

        verify(userService).deleteUser(any(UserDelete.class));
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
                "        \"userId\": 1\n" +
                "    }";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));
        when(userService.haveUserById(anyInt())).thenReturn(false);

        servlet.doDelete(request, response);

        verify(response).sendError(412);
    }

}