package database.adaptator;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import database.res.Data;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;

final class IdMapper <T extends Data> implements JsonDeserializer<T> {

    private static final Logger log = Logger.getLogger(IdMapper.class);

    private Class<?> cls = null;

    public IdMapper(String className) {
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error(e,e);
            e.printStackTrace();
        }
    }


    @Override
    public T deserialize(JsonElement json, Type typeOfT,
                         JsonDeserializationContext context) throws JsonParseException {

        T result = new Gson().fromJson(json, (Type) cls);

        try {
            result.setId(json.getAsJsonObject().get("_id")
                    .getAsJsonObject()
                    .get("$oid").getAsString());

        } catch (Exception e) {
            log.error(e,e);
        }

        return result;
    }
}