package dao.access;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.Interest;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.INTEREST;

public class InterestDao extends DAO<Interest, String, String> {

    @Override
    public Interest insert(Interest obj) {

        Document document = ObjectAdaptator
                .toDocument(obj);

        Connection.getMongoCollection(INTEREST)
                .insertOne(document);

        obj.setId(document.get("_id").toString());

        return obj;
    }

    @Override
    public Boolean delete(String id) {
        DeleteResult result = null;

        result = Connection.getMongoCollection(INTEREST)
                .deleteOne(new Document("_id", new ObjectId(id)));

        return (result.getDeletedCount() > 0);
    }

    @Override
    public Boolean update(Interest obj, String idObject) {

        UpdateResult result = null;

        result = Connection.getMongoCollection(INTEREST)
                .updateOne(Filters.eq("_id", new ObjectId(idObject)),
                        new Document("$set", ObjectAdaptator
                                .toDocument(obj)));

        return (result.getModifiedCount() > 0);

    }

    @Override
    public Interest findOne(String objId) {

        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("_id", new ObjectId(objId)));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        Document doc = (Document) Connection.getMongoCollection(INTEREST)
                .find(whereQuery)
                .first();

        return ((Interest) ObjectAdaptator.fromDocument(doc, INTEREST));
    }

    @Override
    public List<Interest> find(String ine) {
        List<Interest> interests = new ArrayList<>();
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("ine", ine));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(INTEREST)
                .find(whereQuery);

        for (Object object : cursor) {
            interests.add((Interest) ObjectAdaptator.fromDocument((Document) object, INTEREST));
        }

        return interests;
    }

    @Override
    public List<Interest> findByField(String field, String value) {
        return null;
    }


}
