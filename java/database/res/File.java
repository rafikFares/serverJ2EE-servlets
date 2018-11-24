package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

public class File extends Data{


    @SerializedName("ine")
    @Expose
    private String mIne;
    @SerializedName("filename")
    @Expose
    private String mFileName;
    @SerializedName("key")
    @Expose
    private String mKey;
    @SerializedName("location")
    @Expose
    private String mLocation;
    @SerializedName("size")
    @Expose
    private String mSize;
    @SerializedName("profile_picture")
    @Expose
    private Boolean mProfilePicture;

    public File() {
    }

    public File(String mIne, String mFileName, String mKey,
                String mLocation, String mSize, Boolean mProfilePicture) {
        this.mIne = mIne;
        this.mFileName = mFileName;
        this.mKey = mKey;
        this.mLocation = mLocation;
        this.mSize = mSize;
        this.mProfilePicture = mProfilePicture;
    }

    public String getIne() {
        return mIne;
    }

    public void setIne(String mIne) {
        this.mIne = mIne;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getSize() {
        return mSize;
    }

    public Double getSizeAsNumeric() {
        return Double.valueOf(mSize);
    }

    public void setSize(String mSize) {
        this.mSize = mSize;
    }

    public Boolean getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(Boolean mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

    @Override
    public String toString() {
        return "File{" +
                "mIne='" + mIne + '\'' +
                ", mFileName='" + mFileName + '\'' +
                ", mKey='" + mKey + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mSize='" + mSize + '\'' +
                ", mProfilePicture=" + mProfilePicture +
                '}';
    }
}
