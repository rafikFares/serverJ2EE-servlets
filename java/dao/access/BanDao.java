package dao.access;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import dao.DAO;
import database.Connection;
import database.adaptator.ObjectAdaptator;
import database.res.User;
import io.reactivex.annotations.Nullable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.BLACKLIST;
import static database.Utils.USER;

public class BanDao extends DAO<User, String, String> {

    @Override
    public User insert(User obj) {

        Document document = ObjectAdaptator
                .toDocument(obj);

        Connection.getMongoCollection(BLACKLIST)
                .insertOne(document);

        obj.setId(document.get("_id").toString());

        return obj;
    }

    /**
     * info need to be
     * id:1234
     * or
     * ine:1234
     *
     * @param info
     * @return
     */
    @Override
    public Boolean delete(String info) {
        DeleteResult result = null;
        String[] parts = info.split(":");

        result = Connection.getMongoCollection(BLACKLIST)
                .deleteOne(parts[0].equalsIgnoreCase("id") ?
                        new Document("_id", new ObjectId(parts[1])) : new Document("ine", parts[1]));

        return (result.getDeletedCount() > 0);
    }

    @Override
    public Boolean update(User obj, String idObject) {
        return null;
    }

    @Override
    public User findOne(String objId) {
        return null;
    }

    @Override
    public List<User> find(@Nullable String id) {
        List<User> bannedUsers = new ArrayList<>();

        FindIterable cursor = Connection.getMongoCollection(BLACKLIST)
                .find();

        for (Object object : cursor) {
            bannedUsers.add((User) ObjectAdaptator.fromDocument((Document) object, USER));
        }

        return bannedUsers;
    }

    @Override
    public List<User> findByField(String field, String value) {
        return null;
    }

    public List<String> getInes() {
        List<String> bannedUsersIne = new ArrayList<>();

        FindIterable cursor = Connection.getMongoCollection(BLACKLIST)
                .find();

        for (Object object : cursor) {
            bannedUsersIne.add(((User) ObjectAdaptator
                    .fromDocument((Document) object, USER)).getMatricule());
        }

        return bannedUsersIne;
    }

}
