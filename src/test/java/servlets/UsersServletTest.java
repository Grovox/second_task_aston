package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class UsersServletTest {
    UsersServlet servlet;
    UserService userService;
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        servlet = new UsersServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userService = mock(UserService.class);

        Field field = UsersServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userService);
    }

    @Test
    void doGet() throws IOException {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(userService).getAllUsers();
        verify(printWriter).print(anyString());
    }
}