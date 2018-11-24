package servlets.authentification;

import database.adaptator.ObjectAdaptator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static database.security.AccessData.getAccessInstance;
import static servlets.Tools.ERROR;
import static servlets.authentification.security.TokenManager.makeJwt;


@WebServlet("/new")
public class Authentificate extends HttpServlet {

    private static final Logger log = Logger.getLogger(Authentificate.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String appId = req.getParameter("appid");
        String appSecure = req.getParameter("appsecure");
        String mStudent = req.getParameter("student");

        if (mStudent == null || mStudent.equals("")) {

            log.error("error_message" + "student is empty");

            req.setAttribute("error_message", "student is empty");

        } else if (appId.equalsIgnoreCase(getAccessInstance().getResource("AppId"))
                && appSecure.equalsIgnoreCase(getAccessInstance().getResource("AppSecure"))) {

            forwardStudent(req, resp, mStudent);

        } else {
            log.error("ERREUR appid "+appId+" appsecure "+appSecure+" student "+mStudent);
            showError(resp);
        }

    }

    private void forwardStudent(HttpServletRequest req, HttpServletResponse resp,
                                String student) throws IOException {

        String jwt = makeJwt(student);

        log.info("creating jwt for Student : " + student);
        log.info("jwt : " + jwt);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println(ObjectAdaptator.fixJson(
                ObjectAdaptator.toJson(jwt), "jwt"));

    }

    private void showError(HttpServletResponse resp)
            throws IOException {

        resp.setStatus(ERROR);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\" ERROR \"}");
    }

}
