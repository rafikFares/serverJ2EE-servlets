package dao.access;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.Post;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.POSTS;

public class PostDao extends DAO<Post, String, String> {

    @Override
    public Post insert(Post obj) {

        Document document = ObjectAdaptator
                .toDocument(obj);

        Connection.getMongoCollection(POSTS)
                .insertOne(document);

        obj.setId(document.get("_id").toString());

        return obj;
    }

    @Override
    public Boolean delete(String id) {
        DeleteResult result = null;

        result = Connection.getMongoCollection(POSTS)
                .deleteOne(new Document("_id", new ObjectId(id)));

        return (result.getDeletedCount() > 0);
    }

    @Override
    public Boolean update(Post obj, String idObject) {

        UpdateResult result = null;

        result = Connection.getMongoCollection(POSTS)
                .updateOne(Filters.eq("_id", new ObjectId(idObject)),
                        new Document("$set", ObjectAdaptator
                                .toDocument(obj)));

        return (result.getModifiedCount() > 0);

    }

    @Override
    public Post findOne(String objId) {

        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("_id", new ObjectId(objId)));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        Document doc = (Document) Connection.getMongoCollection(POSTS)
                .find(whereQuery)
                .first();

        return ((Post) ObjectAdaptator.fromDocument(doc, POSTS));
    }

    @Override
    public List<Post> find(String idPoster) {
        List<Post> posts = new ArrayList<>();
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("idposter", idPoster));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(POSTS)
                .find(whereQuery);

        for (Object object : cursor) {
            posts.add((Post) ObjectAdaptator.fromDocument((Document) object, POSTS));
        }

        return posts;
    }

    @Override
    public List<Post> findByField(String field, String value) {
        List<Post> posts = new ArrayList<>();

        List objQueryAnd = new ArrayList();
        List objQueryOr = new ArrayList();

        String[] parts = value.split(":");

        objQueryAnd.add(new BasicDBObject("published", true));

        for (String part : parts) {
            objQueryOr.add(new BasicDBObject(field,
                    new Document("$regex", part)
                            .append("$options", "$i")));
        }

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQueryAnd);
        whereQuery.put("$or", objQueryOr);

        FindIterable cursor = Connection.getMongoCollection(POSTS)
                .find(whereQuery);

        for (Object object : cursor) {
            posts.add((Post) ObjectAdaptator.fromDocument((Document) object, POSTS));
        }

        return posts;
    }


    public List<Post> findHomePosts(String limit) {
        List<Post> posts = new ArrayList<>();

        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("published", true));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(POSTS)
                .find(whereQuery).sort(new BasicDBObject("_id", -1))
                .limit((limit == null || limit.equalsIgnoreCase("")) ? 20 : Integer.valueOf(limit));

        int index = 0;
        for (Object object : cursor) {
            posts.add((Post) ObjectAdaptator.fromDocument((Document) object, POSTS));
            index++;
        }

        return posts;
    }

    public Long countPublishedPostOf(String idPoster) {
        List<Post> posts = new ArrayList<>();
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("idposter", idPoster));
        objQuery.add(new BasicDBObject("published", true));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        return Connection.getMongoCollection(POSTS)
                .count(whereQuery);

    }


}
