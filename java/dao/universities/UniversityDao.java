package dao.universities;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.University;
import org.apache.log4j.Logger;
import org.bson.Document;
import dao.universities.IUniManager;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.UNIVERSITY;

public class UniversityDao extends DAO<University, String, String>
        implements IUniManager {

    private static final Logger log = Logger.getLogger(UniversityDao.class);


    @Override
    public String getUniversityPortailLink(String uniName) {

        List objQuesry = new ArrayList();
        objQuesry.add(new BasicDBObject("uniName", uniName));

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("$and", objQuesry);
        log.info("Sql query is : " + whereQuery.toString());

        Document doc = (Document) Connection.getMongoCollection(UNIVERSITY)
                .find(whereQuery)
                .first();

        return ((University)ObjectAdaptator.fromDocument(doc, UNIVERSITY))
                .getPortailLink();

    }

    @Override
    public University insert(University university) {
        Connection.getMongoCollection(UNIVERSITY)
                .insertOne(ObjectAdaptator
                        .toDocument(university));
        log.info("INSERT NEW UNI "+university.toString());
        return university;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    @Override
    public Boolean update(University obj, String idObject) {
        return null;
    }

    @Override
    public University findOne(String id) {
        return null;
    }

    /**
     * Only for tests
     *
     * @return the lists of all universities in the BDD
     */
    @Override
    public List<University> find(String id) {
        List<University> universities = new ArrayList<>();
        FindIterable cursor = Connection.getMongoCollection(UNIVERSITY)
                .find();

        for (Object object : cursor) {
            universities.add((University) ObjectAdaptator.fromDocument((Document) object, UNIVERSITY));
        }

        log.warn("trying to get all the list of universities");

        return universities;
    }

    @Override
    public List<University> findByField(String field, String value) {
        return null;
    }
}
