package database.adaptator;

import com.google.gson.*;
import database.res.Post;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


final class PostMapper implements JsonDeserializer<Post> {

    private static final Logger log = Logger.getLogger(PostMapper.class);

    private static DateFormat dateFormat = null;

    @Override
    public Post deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {

        Post result = new Gson().fromJson(json, Post.class);

        try {
            result.setId(json.getAsJsonObject().get("_id")
                    .getAsJsonObject()
                    .get("$oid").getAsString());



            result.setCreated_at(getDateFormat().format(new Date(Long.parseLong(json.getAsJsonObject().get("created_at")
                    .getAsJsonObject()
                    .get("$date").getAsString()))));

            result.setUpdated_at(getDateFormat().format(new Date(Long.parseLong(json.getAsJsonObject().get("updated_at")
                    .getAsJsonObject()
                    .get("$date").getAsString()))));


        } catch (Exception e) {
            log.error(e,e);
        }

        return result;
    }

    private static DateFormat getDateFormat(){
        if (dateFormat == null){
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return dateFormat;
    }
}