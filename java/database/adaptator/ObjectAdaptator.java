package database.adaptator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import database.res.*;
import extern.books_api.res.Book;
import org.bson.Document;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static database.Utils.*;

public final class ObjectAdaptator {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static DBObject toDBObject(Object object) {
        if (object instanceof University) {
            return new BasicDBObject("uniName", ((University) object).getUniName())
                    .append("portailLink", ((University) object).getPortailLink());

        } else if (object instanceof User) {
            return new BasicDBObject("ine", ((User) object).getMatricule());

        } else if (object instanceof Post) {
            return new BasicDBObject("idposter", ((Post) object).getIdPoster())
                    .append("titre", ((Post) object).getTitre())
                    .append("contenue", ((Post) object).getContenue())
                    .append("tags", ((Post) object).getTags())
                    .append("published", ((Post) object).getPublished())
                    .append("created_at", ((Post) object).getCreated_at())
                    .append("updated_at", ((Post) object).getUpdated_at());

        } else if (object instanceof Student) {

        }
        return null;
    }

    public static Document toDocument(Object object) {
        if (object instanceof University) {
            return new Document("uniName", ((University) object).getUniName())
                    .append("portailLink", ((University) object).getPortailLink());

        } else if (object instanceof User) {
            return new Document("ine", ((User) object).getMatricule());

        } else if (object instanceof Post) {
            return new Document("idposter", ((Post) object).getIdPoster())
                    .append("titre", ((Post) object).getTitre())
                    .append("contenue", ((Post) object).getContenue())
                    .append("tags", ((Post) object).getTags())
                    .append("published", ((Post) object).getPublished())
                    .append("created_at", ((Post) object).getDateCreate())
                    .append("updated_at", ((Post) object).getDateUpdate());

        } else if (object instanceof Student) {

        } else if (object instanceof File) {
            return new Document("ine", ((File) object).getIne())
                    .append("filename", ((File) object).getFileName())
                    .append("key", ((File) object).getKey())
                    .append("location", ((File) object).getLocation())
                    .append("size", ((File) object).getSize())
                    .append("profile_picture", ((File) object).getProfilePicture());

        } else if (object instanceof Interest) {
            return new Document("ine", ((Interest) object).getIne())
                    .append("preference", ((Interest) object).getInterest());

        } else if (object instanceof Comment) {
            return new Document("ine", ((Comment) object).getIne())
                    .append("id_post", ((Comment) object).getIdPost())
                    .append("content", ((Comment) object).getContent())
                    .append("created_at", ((Comment) object).getDateCreate());

        }
        return null;
    }


    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }


    public static String toSlug(String input) {
        if (input == null)
            throw new IllegalArgumentException();

        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }


    public static Map<String, String> toMap(Object object) {
        if (object instanceof Student) {
            return new HashMap<>() {{
                put("ine", ((Student) object).getIne());
                put("matricule", String.valueOf(((Student) object).getMatricule()));
                put("username", ((Student) object).getUserName());
                put("password", ((Student) object).getPass());
                put("firstname", ((Student) object).getFirstName());
                put("lastname", ((Student) object).getLastName());
                put("datebirth", ((Student) object).getDateOfBirth());
                put("placebirth", ((Student) object).getPlaceOfBirth());
                put("email", ((Student) object).getEmail());
            }};

        }

        return null;
    }


    public static Object fromJson(String json, int arg) {
        if (arg == STUDENT) {
            return new Gson().fromJson(json, Student.class);

        } else if (arg == UNIVERSITY) {
            return new Gson().fromJson(json, University.class);

        } else if (arg == USER) {
            return new GsonBuilder().registerTypeAdapter(User.class,
                    new IdMapper<User>(User.class.getTypeName())).create().fromJson(json, User.class);

        } else if (arg == POSTS) {
            return new GsonBuilder().registerTypeAdapter(Post.class,
                    new PostMapper()).create().fromJson(json, Post.class);

        } else if (arg == BOOK) {
            return new Gson().fromJson(json, Book.class);

        } else if (arg == FILE) {
            return new GsonBuilder().registerTypeAdapter(File.class,
                    new IdMapper<File>(File.class.getTypeName())).create().fromJson(json, File.class);

        } else if (arg == INTEREST) {
            return new GsonBuilder().registerTypeAdapter(Interest.class,
                    new IdMapper<Interest>(Interest.class.getTypeName())).create().fromJson(json, Interest.class);

        } else if (arg == COMMENT) {
            return new GsonBuilder().registerTypeAdapter(Comment.class,
                    new CommentMapper()).create().fromJson(json, Comment.class);

        }
        return null;
    }


    public static Object fromDocument(Document document, int arg) {
        if (document != null)
            return fromJson(document.toJson(), arg);
        return null;
    }


    public static String fixJson(String json, String attribute) {
        return "{\"" + attribute + "\": " + json + "}";
    }


}
