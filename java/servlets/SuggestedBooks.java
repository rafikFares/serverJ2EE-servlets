package servlets;

import database.adaptator.ObjectAdaptator;
import extern.GoogleBooks;
import extern.IGooglePresenter;
import extern.books_api.res.Book;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static servlets.Tools.ERROR;

@WebServlet("/get_suggested_books")
public class SuggestedBooks extends HttpServlet {

    private static final Logger log = Logger.getLogger(SuggestedBooks.class);


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String theme = req.getParameter("subject");
        String max = req.getParameter("max");

        log.info("choosed subject is : " + theme + " max is " + max);

        IGooglePresenter callBack = new GoogleBooks();

        if (theme == null || theme.equals("")) {
            log.error("error_message" + "theme is empty");
            showError(resp);
        } else {
            handleRequest(req, resp, callBack, theme, max);
        }


    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp,
                               IGooglePresenter callback, String theme, String max)
            throws ServletException, IOException {

        List<Book> books = callback.getPreferedBooks(theme, max);
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println(ObjectAdaptator
                .fixJson(
                        ObjectAdaptator
                                .toJson(books),
                        "Books"));

    }

    private void showError(HttpServletResponse resp)
            throws IOException {

        log.error("showError");

        resp.setStatus(ERROR);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\" ERROR \"}");
    }

}
