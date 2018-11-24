package servlets;

import dao.Factory;
import database.adaptator.ObjectAdaptator;
import database.res.University;
import dao.universities.IUniManager;
import dao.universities.UniversityDao;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static database.Utils.UNIVERSITY;
import static servlets.Tools.ADMIN_ACCESS;
import static servlets.Tools.NOT_FOUND;
import static servlets.Tools.SUCCESS;

@WebServlet(name = "uniServlet", urlPatterns = {"/portail", "/admin/portail"})
public class Portail extends HttpServlet {

    private static final Logger log = Logger.getLogger(Portail.class);


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if ((req.getRequestURI().equalsIgnoreCase("/admin/portail")
                || req.getRequestURI().equalsIgnoreCase("/DarProject/admin/portail"))
                && req.getParameter("access").equalsIgnoreCase("ADMIN")) {

            String fac = req.getParameter("university");
            String link = req.getParameter("lien");
            Factory.getDao(UNIVERSITY)
                    .insert(new University(fac, link));
            resp.setStatus(200);

        } else {
            log.fatal("need to have admin access");
            showAdminAccess(resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String tmpUniLink ;
        String fac = req.getParameter("university");
        String appid = req.getParameter("appid");
        log.info("choosed university is : " + fac);

        UrlResult urlResult = getUrl(new UniversityDao(), fac);

        tmpUniLink = urlResult.url +"?appid="+ appid;

        log.info("redirection link is : " + tmpUniLink);
        resp.setStatus(urlResult.status);

        urlResult.url = tmpUniLink;

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println(ObjectAdaptator.toJson(urlResult));

    }

    private UrlResult getUrl(IUniManager callBack, String uniName) {
        return new UrlResult(callBack.getUniversityPortailLink(uniName));
    }

    private void showAdminAccess(HttpServletResponse resp)
            throws IOException {

        resp.setStatus(ADMIN_ACCESS);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"need to have admin access \"}");
    }

    private class UrlResult {
        String url = null;
        int status;

        public UrlResult(String url) {
            this.url = url;
            if (this.url != null) {
                status = SUCCESS;
            } else {
                status = NOT_FOUND;
            }
        }
    }

}


