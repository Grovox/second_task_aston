package servlets;

import convectors.BuyBookConvector;
import services.BuyBookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BuyBooksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = BuyBookConvector.allBuyBooksToJsonString(BuyBookService.getAllBuyBooks());
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
