package servlets;

import dao.Factory;
import dao.access.FileDao;
import database.adaptator.ObjectAdaptator;
import database.res.File;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static database.Utils.FILE;
import static servlets.Tools.*;
import static servlets.authentification.security.TokenManager.getUserIne;

@WebServlet("/files")
public class FileManager extends HttpServlet {

    private static final Logger log = Logger.getLogger(FileManager.class);


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {
            String filename = req.getParameter("filename");
            String key = req.getParameter("key");
            String location = req.getParameter("location");
            String size = req.getParameter("size");
            Boolean profilePicture = !req.getParameter("profile_picture").equalsIgnoreCase("false");

            File file = new File(ine, filename, key, location, size, profilePicture);

            Factory.getDao(FILE)
                    .insert(file);

            log.info("ADD of "+file.toString());

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.toJson(file));
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
            switch (req.getParameter("type")) {
                case "all": {
                    List<File> tmp = Factory.getDao(FILE)
                            .find(ine);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    out.println(ObjectAdaptator.fixJson(
                            ObjectAdaptator.toJson(tmp), "files"));
                    break;
                }
                case "profile": {
                    File tmp = ((FileDao) Factory.getDao(FILE))
                            .findProfilePicOf(ine);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    out.println(ObjectAdaptator.fixJson(
                            ObjectAdaptator.toJson(tmp), "file"));
                    break;
                }
                case "size": {
                    Double tmp = ((FileDao) Factory.getDao(FILE))
                            .countFilesSizeOf(ine);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    out.println(ObjectAdaptator.fixJson(
                            tmp.toString(), "size"));
                    break;
                }
                default:{
                    log.error("Error delete");
                    showError(resp);
                }
            }
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
            String key = req.getParameter("id");

            if (Factory.getDao(FILE)
                    .delete(key)) {
                log.info("DELETE OF "+key);
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
