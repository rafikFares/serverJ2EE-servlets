package dao.access;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.Comment;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.COMMENT;

public class CommentDao extends DAO<Comment, String, String> {

    @Override
    public Comment insert(Comment obj) {

        Document document = ObjectAdaptator
                .toDocument(obj);

        Connection.getMongoCollection(COMMENT)
                .insertOne(document);

        obj.setId(document.get("_id").toString());

        return obj;
    }

    @Override
    public Boolean delete(String id) {
        DeleteResult result = null;

        result = Connection.getMongoCollection(COMMENT)
                .deleteOne(new Document("_id", new ObjectId(id)));

        return (result.getDeletedCount() > 0);
    }

    @Override
    public Boolean update(Comment obj, String idObject) {

        UpdateResult result = null;

        result = Connection.getMongoCollection(COMMENT)
                .updateOne(Filters.eq("_id", new ObjectId(idObject)),
                        new Document("$set", ObjectAdaptator
                                .toDocument(obj)));

        return (result.getModifiedCount() > 0);

    }

    @Override
    public Comment findOne(String objId) {

        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("_id", new ObjectId(objId)));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        Document doc = (Document) Connection.getMongoCollection(COMMENT)
                .find(whereQuery)
                .first();

        return ((Comment) ObjectAdaptator.fromDocument(doc, COMMENT));
    }

    @Override
    public List<Comment> find(String ine) {
        List<Comment> comments = new ArrayList<>();
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("ine", ine));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(COMMENT)
                .find(whereQuery);

        for (Object object : cursor) {
            comments.add((Comment) ObjectAdaptator.fromDocument((Document) object, COMMENT));
        }

        return comments;
    }

    @Override
    public List<Comment> findByField(String field, String value) {
        List<Comment> comments = new ArrayList<>();
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject(field, value));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(COMMENT)
                .find(whereQuery);

        for (Object object : cursor) {
            comments.add((Comment) ObjectAdaptator.fromDocument((Document) object, COMMENT));
        }

        return comments;
    }


}
