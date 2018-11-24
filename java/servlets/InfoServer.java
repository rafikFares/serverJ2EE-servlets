package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static database.security.AccessData.getAccessInstance;
import static servlets.Tools.SUCCESS;

@WebServlet("/infos")
public class InfoServer extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setStatus(SUCCESS);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" homeLink \":"
                +"\""+getAccessInstance().getResource("HomePageLink")+" \","
                +"\" env \":"
                +"\""+getAccessInstance().getEnvironment()+" \","
                +"\" MongoPort \":"
                +"\""+getAccessInstance().getResource("MongoPort")+" \","
                +"\" MongoUrl \":"
                +"\""+getAccessInstance().getResource("MongoUrl")+" \","
                +"\" MongoUsername \":"
                +"\""+getAccessInstance().getResource("MongoUsername")+" \","
                +"\" MongoPassword \":"
                +"\""+getAccessInstance().getResource("MongoPassword")+" \","
                +"\" MongoDbName \":"
                +"\""+getAccessInstance().getResource("MongoDbName")+" \","
                +"\" AppId \":"
                +"\""+getAccessInstance().getResource("AppId")+" \","
                +"\" AppSecure \":"
                +"\""+getAccessInstance().getResource("AppSecure")+" \","
                + "}");

    }
}
