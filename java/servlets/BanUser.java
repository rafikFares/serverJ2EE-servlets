package servlets;

import dao.Factory;
import database.adaptator.ObjectAdaptator;
import database.res.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static database.Utils.BLACKLIST;
import static servlets.Tools.*;
import static servlets.authentification.security.TokenManager.getUserIne;

@WebServlet(name = "banServlet", urlPatterns = {"/ban", "/admin/blacklist"})
public class BanUser extends HttpServlet {

    private static final Logger log = Logger.getLogger(BanUser.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            String blackIne = req.getParameter("black_ine");

            User user = new User(blackIne);

            Factory.getDao(BLACKLIST)
                    .insert(user);

            log.info("BANNING "+user.toString());

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.fixJson(
                    ObjectAdaptator.toJson(user), "banned"));
        } else {
            log.warn("UNAUTHORIZED TOKEN "+token);
            showUnauthorized(resp);
        }

    }


    /**
     * Only for admins
     * need valid Token + url = "/admin/blacklist" + field : access = ADMIN
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null && (req.getRequestURI().equalsIgnoreCase("/admin/blacklist")
                || req.getRequestURI().equalsIgnoreCase("/DarProject/admin/blacklist"))
                && req.getParameter("access").equalsIgnoreCase("ADMIN")) {

            List<User> tmp = Factory.getDao(BLACKLIST)
                    .find(null);

            log.warn("getting blacklist");

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.fixJson(
                    ObjectAdaptator.toJson(tmp), "blacklist"));
        } else {
            log.fatal("need admin access UNAUTHORIZED TOKEN "+token);
            showAdminAccess(resp);
        }
    }


    /**
     * Only for admins
     * need valid Token + url = "/admin/blacklist" + field : access = ADMIN
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null && (req.getRequestURI().equalsIgnoreCase("/admin/blacklist")
                || req.getRequestURI().equalsIgnoreCase("/DarProject/admin/blacklist"))
                && req.getParameter("access").equalsIgnoreCase("ADMIN")) {

            /**
             * give only one parameter id = 123 or ine = 123 of the user to UnBan -> remove it from blackList
             */
            String byId = req.getParameter("id");
            String byIne = req.getParameter("ine");

            if (Factory.getDao(BLACKLIST)
                    .delete((byId != null && !byId.equalsIgnoreCase("")) ? "id:" + byId : "ine:" + byIne)) {

                log.info("Removing user from blacklist "+byId+" "+byIne);
                showSuccess(resp);
            } else {
                log.error("ERROR DELETE OF "+byId+" "+byIne);
                showError(resp);
            }
        } else {
            log.fatal("need admin access UNAUTHORIZED TOKEN "+token);
            showAdminAccess(resp);
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

    private void showAdminAccess(HttpServletResponse resp)
            throws IOException {

        System.err.println();

        resp.setStatus(ADMIN_ACCESS);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"need to have admin access \"}");
    }

    private void showError(HttpServletResponse resp)
            throws IOException {

        resp.setStatus(ERROR);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"ERROR DELETE \"}");
    }
}
