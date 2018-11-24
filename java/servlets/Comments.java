package servlets;

import dao.Factory;
import database.adaptator.ObjectAdaptator;
import database.res.Comment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Utils.COMMENT;
import static servlets.Tools.ERROR;
import static servlets.Tools.FORBIDEN;
import static servlets.Tools.SUCCESS;
import static servlets.authentification.security.TokenManager.getUserIne;


@WebServlet("/comments")
public class Comments extends HttpServlet {

    private static final Logger log = Logger.getLogger(Comments.class);


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            String idPost = req.getParameter("id_post");
            String content = req.getParameter("content");

            Comment comment = new Comment(ine, idPost, content);

            Factory.getDao(COMMENT)
                    .insert(comment);

            log.info("ADD of "+comment.toString());

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.toJson(comment));

        } else {
            log.warn("UNAUTHORIZED TOKEN "+token);
            showUnauthorized(resp);
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idPost = req.getParameter("id_post");

        List<Comment> tmp = Factory.getDao(COMMENT)
                .findByField("id_post", idPost);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println(ObjectAdaptator.fixJson(
                ObjectAdaptator.toJson(tmp), "comments"));

    }


    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            String idComment = req.getParameter("id");

            if (Factory.getDao(COMMENT)
                    .delete(idComment)) {
                log.warn("DELETE OF "+idComment);
                showSuccess(resp);
            } else {
                log.error("Error delete");
                showError(resp);
            }
        } else {
            log.warn("UNAUTHORIZED TOKEN "+token);
            showUnauthorized(resp);
        }
    }

    private void showSuccess(HttpServletResponse resp)
            throws IOException {

        resp.setStatus(SUCCESS);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"success \"}");
    }

    private void showUnauthorized(HttpServletResponse resp)
            throws IOException {

        resp.setStatus(FORBIDEN);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"not authorized \"}");
    }

    private void showError(HttpServletResponse resp)
            throws IOException {

        resp.setStatus(ERROR);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"ERROR DELETE \"}");
    }


}
