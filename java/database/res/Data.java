package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

public class Data {

    @SerializedName("id")
    @Expose
    protected String mId;

    @SerializedName("_id")
    @Expose
    protected ObjectId mIdAsObject;

    public Data(String mId) {
        this.mId = mId;
    }

    public Data(String mId, ObjectId mIdAsObject) {
        this.mId = mId;
        this.mIdAsObject = mIdAsObject;
    }

    public Data() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public ObjectId getIdAsObject() {
        return mIdAsObject;
    }

    public void setIdAsObject(ObjectId mIdAsObject) {
        this.mIdAsObject = mIdAsObject;
    }

    @Override
    public String toString() {
        return "Data{" +
                "mId='" + mId + '\'' +
                ", mIdAsObject=" + mIdAsObject +
                '}';
    }
}
