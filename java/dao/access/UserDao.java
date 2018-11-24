package dao.access;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.User;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.USER;

public class UserDao extends DAO<User, String, String> {

    @Override
    public User insert(User obj) {

        Document document = ObjectAdaptator
                .toDocument(obj);

        Connection.getMongoCollection(USER)
                .insertOne(document);

        obj.setId(document.get("_id").toString());

        return obj;
    }

    @Override
    public Boolean delete(String id) {
        DeleteResult result = null;

        result = Connection.getMongoCollection(USER)
                .deleteOne(new Document("_id", new ObjectId(id)));

        return (result.getDeletedCount() > 0);
    }

    @Override
    public Boolean update(User obj, String idObject) {

        UpdateResult result = null;

        result = Connection.getMongoCollection(USER)
                .updateOne(Filters.eq("_id", new ObjectId(idObject)),
                        new Document("$set", ObjectAdaptator
                                .toDocument(obj)));

        return (result.getModifiedCount() > 0 );

    }

    @Override
    public User findOne(String objId) {
        List objQuesry = new ArrayList();
        objQuesry.add(new BasicDBObject("_id", new ObjectId(objId)));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuesry);

        Document doc = (Document) Connection.getMongoCollection(USER)
                .find(whereQuery)
                .first();

        return ((User) ObjectAdaptator.fromDocument(doc, USER));

    }

    @Override
    public List<User> find(String id) {
        return null;
    }

    @Override
    public List<User> findByField(String field, String value) {
        return null;
    }


}
