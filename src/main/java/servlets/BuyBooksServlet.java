package servlets;

import mapper.BuyBookMapper;
import services.BuyBookService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BuyBooksServlet extends HttpServlet {
    private BuyBookService buyBookService;

    @Override
    public void init() {
        buyBookService = BuyBookService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String json = BuyBookMapper.allBuyBooksToJsonString(buyBookService.getAllBuyBooks());
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
