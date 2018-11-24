package servlets;

import dao.Factory;
import database.adaptator.ObjectAdaptator;
import database.res.Interest;
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

import static database.Utils.INTEREST;
import static servlets.Tools.ERROR;
import static servlets.Tools.FORBIDEN;
import static servlets.Tools.SUCCESS;
import static servlets.authentification.security.TokenManager.getUserIne;

@WebServlet("/preferences")
public class Preferences extends HttpServlet {

    private static final Logger log = Logger.getLogger(Preferences.class);


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            String preference = req.getParameter("preference");

            Interest interest = new Interest(ine, preference);

            Factory.getDao(INTEREST)
                    .insert(interest);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.toJson(interest));
        } else {
            log.warn("UNAUTHORIZED TOKEN "+token);
            showUnauthorized(resp);
        }

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            List<Interest> tmpList = Factory.getDao(INTEREST)
                    .find(ine);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.fixJson(
                    ObjectAdaptator.toJson(tmpList), "preferences"));
        } else {
            log.warn("UNAUTHORIZED TOKEN "+token);
            showUnauthorized(resp);
        }

    }


    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            String idPref = req.getParameter("id");

            log.warn("DELETE "+idPref);

            if (Factory.getDao(INTEREST)
                    .delete(idPref)) {

                showSuccess(resp);
            } else {
                showError(resp);
            }
        } else {
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
