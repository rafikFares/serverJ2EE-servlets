package servlets;

import dao.Factory;
import dao.access.PostDao;
import database.adaptator.ObjectAdaptator;
import database.res.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static database.Utils.POSTS;
import static database.security.AccessData.getAccessInstance;

@WebServlet(name = "homeServlet", urlPatterns = {"/home", "/post/latest"})
public class Home extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String url = req.getRequestURI();

        if (url.equalsIgnoreCase("/home")
                || url.equalsIgnoreCase("/DarProject/home")) {

            resp.sendRedirect(getAccessInstance().getResource("HomePageLink"));

        } else if (url.equalsIgnoreCase("/post/latest")
                || url.equalsIgnoreCase("/DarProject/post/latest")) {

            String limit = req.getParameter("limit");

            List<Post> tmpList = ((PostDao) Factory.getDao(POSTS))
                    .findHomePosts(limit);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.fixJson(
                    ObjectAdaptator.toJson(tmpList), "posts"));
        }

    }


}
