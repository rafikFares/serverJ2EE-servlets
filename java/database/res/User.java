package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

public class User extends Data {

    @SerializedName("ine")
    @Expose
    private String mMatricule;

    public User() {
    }

    public User(String mId, String mMatricule) {
        super(mId);
        this.mMatricule = mMatricule;
    }

    public User(String mId, ObjectId mIdAsObject, String mMatricule) {
        super(mId, mIdAsObject);
        this.mMatricule = mMatricule;
    }

    public User(String mMatricule) {
        this.mMatricule = mMatricule;
    }

    public String getMatricule() {
        return mMatricule;
    }

    public void setMatricule(String mMatricule) {
        this.mMatricule = mMatricule;
    }

    @Override
    public String toString() {
        return "User{" +
                "mMatricule=" + mMatricule +
                '}';
    }
}
