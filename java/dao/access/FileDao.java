package dao.access;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.File;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.FILE;

public class FileDao extends DAO<File, String, String> {

    @Override
    public File insert(File obj) {

        Document document = ObjectAdaptator
                .toDocument(obj);

        Connection.getMongoCollection(FILE)
                .insertOne(document);

        obj.setId(document.get("_id").toString());

        return obj;
    }

    @Override
    public Boolean delete(String id) {
        DeleteResult result = null;

        result = Connection.getMongoCollection(FILE)
                .deleteOne(new Document("_id", new ObjectId(id)));

        return (result.getDeletedCount() > 0);
    }

    @Override
    public Boolean update(File obj, String idObject) {

        UpdateResult result = null;

        result = Connection.getMongoCollection(FILE)
                .updateOne(Filters.eq("_id", new ObjectId(idObject)),
                        new Document("$set", ObjectAdaptator
                                .toDocument(obj)));

        return (result.getModifiedCount() > 0);

    }

    @Override
    public File findOne(String objId) {

        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("_id", new ObjectId(objId)));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        Document doc = (Document) Connection.getMongoCollection(FILE)
                .find(whereQuery)
                .first();

        return ((File) ObjectAdaptator.fromDocument(doc, FILE));
    }

    @Override
    public List<File> find(String ine) {
        List<File> files = new ArrayList<>();
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("ine", ine));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(FILE)
                .find(whereQuery);

        for (Object object : cursor) {
            files.add((File) ObjectAdaptator.fromDocument((Document) object, FILE));
        }

        return files;
    }

    public File findProfilePicOf(String ine) {

        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("profile_picture", true));
        objQuery.add(new BasicDBObject("ine", ine));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        Document doc = (Document) Connection.getMongoCollection(FILE)
                .find(whereQuery)
                .first();

        return ((File) ObjectAdaptator.fromDocument(doc, FILE));
    }

    public Double countFilesSizeOf(String ine) {
        Double filesSize = 0.0;
        List objQuery = new ArrayList();
        objQuery.add(new BasicDBObject("ine", ine));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuery);

        FindIterable cursor = Connection.getMongoCollection(FILE)
                .find(whereQuery);

        for (Object object : cursor) {
            filesSize += ((File) ObjectAdaptator.fromDocument((Document) object, FILE)).getSizeAsNumeric();
        }

        return filesSize;
    }

    @Override
    public List<File> findByField(String field, String value) {
        return null;
    }

}
