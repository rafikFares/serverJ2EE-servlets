package servlets;

import dao.Factory;
import dao.access.PostDao;
import database.adaptator.ObjectAdaptator;
import database.res.Post;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static database.Utils.POSTS;
import static servlets.Tools.ERROR;
import static servlets.Tools.FORBIDEN;
import static servlets.Tools.SUCCESS;
import static servlets.authentification.security.TokenManager.getUserIne;
import static servlets.authentification.security.TokenManager.verifyToken;

@WebServlet("/posts")
public class Posts extends HttpServlet {

    private static final Logger log = Logger.getLogger(Posts.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");
        String ine = getUserIne(token);

        if (ine != null) {

            String titre = req.getParameter("titre");
            String contenue = req.getParameter("contenue");
            String[] tags = req.getParameterValues("tags");
            Boolean publish = !req.getParameter("published").equalsIgnoreCase("false");

            switch (req.getHeader("methode")) {
                case "add": {

                    log.info("ADD "
                            +"ine : " + ine
                            + " titre : " + titre
                            + " contenue : " + contenue
                            + " tags : " + Arrays.toString(tags)
                            + " publish : " + publish);

                    makeAdd(req, resp, new Post(ine,
                            titre,
                            contenue,
                            (tags != null && tags.length > 0) ?
                                    Arrays.asList(tags) : (List<String>) null,
                            publish));
                    break;
                }
                case "update": {
                    String idposter = req.getParameter("idposter");

                    if (idposter.equalsIgnoreCase(ine)){
                        String idPost = req.getParameter("id_post");
                        String createdAt = req.getParameter("created_at");

                        log.info("UPDATE "
                                + "ine : " + ine
                                + " titre : " + titre
                                + " contenue : " + contenue
                                + " tags : " + Arrays.toString(tags)
                                + " id_post : " + idPost
                                + " publish : " + publish
                                + " created_at : " + createdAt);

                        makeUpdate(req, resp, new Post(ine,
                                new ObjectId(idPost),
                                idPost,
                                titre,
                                contenue,
                                (tags != null && tags.length > 0) ?
                                        Arrays.asList(tags) : (List<String>) null,
                                publish,
                                createdAt));
                    }else {
                        showUnauthorized(resp);
                    }
                    break;
                }
                default:
                    showError(resp);
            }
        } else {
            showUnauthorized(resp);
        }

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");

            switch (req.getParameter("type")) {
                case "one":                            // http://localhost:9000/posts?type=one&id=5bcc73484563c94b95472566
                {
                    String id = req.getParameter("id");
                    Post tmp = (Post) Factory.getDao(POSTS)
                            .findOne(id);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    out.println(ObjectAdaptator.fixJson(
                            ObjectAdaptator.toJson(tmp), "post"));
                    break;
                }

                case "multi":                          // http://localhost:9000/posts?type=multi&id_poster=6666
                {
                    String ine = getUserIne(token);
                    String idPoster = req.getParameter("id_poster");
                        if (ine != null && idPoster.equalsIgnoreCase(ine)) {
                        List<Post> tmpList = Factory.getDao(POSTS)
                                .find(idPoster);

                        PrintWriter out = resp.getWriter();
                        resp.setContentType("application/json");
                        out.println(ObjectAdaptator.fixJson(
                                ObjectAdaptator.toJson(tmpList), "posts"));
                        break;
                    } else {
                        showUnauthorized(resp);
                    }
                    
                }

                case "tag":                             // http://localhost:9000/posts?type=tag&tags=TAG1:TAG2
                {
                    String tags = req.getParameter("tags");

                    List<Post> tmpList = Factory.getDao(POSTS)
                            .findByField("tags", tags);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    out.println(ObjectAdaptator.fixJson(
                            ObjectAdaptator.toJson(tmpList), "posts"));
                    break;
                }

                case "countAll":                          // http://localhost:9000/posts?type=countAll&id_poster=6666
                {
                    String ine = getUserIne(token);
                    String idPoster = req.getParameter("id_poster");
                    if (ine != null && idPoster.equalsIgnoreCase(ine)) {
                        List<Post> tmpList = Factory.getDao(POSTS)
                                .find(idPoster);

                        PrintWriter out = resp.getWriter();
                        resp.setContentType("application/json");
                        out.println(ObjectAdaptator.fixJson(
                                String.valueOf(tmpList.size()), "countAll"));
                        break;
                    } else {
                        showUnauthorized(resp);
                    }

                }

                case "countPublished":                          // http://localhost:9000/posts?type=countPublished&id_poster=6666
                {
                    String ine = getUserIne(token);
                    String idPoster = req.getParameter("id_poster");
                    if (ine != null && idPoster.equalsIgnoreCase(ine)) {
                        Long tmpCount = ((PostDao)Factory.getDao(POSTS))
                                .countPublishedPostOf(idPoster);

                        PrintWriter out = resp.getWriter();
                        resp.setContentType("application/json");
                        out.println(ObjectAdaptator.fixJson(
                                String.valueOf(tmpCount), "countPublished"));
                        break;
                    } else {
                        showUnauthorized(resp);
                    }

                }

                default:
                    showError(resp);
            }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (verifyToken(req.getHeader("token"))) {
            String idPost = req.getParameter("id_post"); // http://localhost:9000/posts?id_post=5bd0cbf5397f803b6a1d5ed6

            log.warn("DELETE "+idPost);

            if (Factory.getDao(POSTS)
                    .delete(idPost)) {
                showSuccess(resp);
            } else {
                showError(resp);
            }
        } else {
            showUnauthorized(resp);
        }

    }

    private void makeAdd(HttpServletRequest req, HttpServletResponse resp,
                         Post post) throws IOException {

        Factory.getDao(POSTS)
                .insert(post);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println(ObjectAdaptator.toJson(post));

    }


    private void makeUpdate(HttpServletRequest req, HttpServletResponse resp,
                            Post post) throws IOException {

        if (Factory.getDao(POSTS)
                .update(post, post.getIdAsObject().toString())) {

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.println(ObjectAdaptator.toJson(post));

        } else {

            showError(resp);

        }

    }

    private void showSuccess(HttpServletResponse resp)
            throws IOException {

        log.info("showSuccess");

        resp.setStatus(SUCCESS);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"success \"}");
    }

    private void showUnauthorized(HttpServletResponse resp)
            throws IOException {

        log.error("showUnauthorized");

        resp.setStatus(FORBIDEN);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("{\" result \":\"not authorized \"}");
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
