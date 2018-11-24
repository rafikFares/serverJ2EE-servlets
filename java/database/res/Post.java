package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Post extends Data{


    @SerializedName("idposter")
    @Expose
    private String mIdPoster; // user who posted the post
    @SerializedName("titre")
    @Expose
    private String mTitre;
    @SerializedName("contenue")
    @Expose
    private String mContenue;
    @SerializedName("tags")
    @Expose
    private List<String> mTags;
    @SerializedName("published")
    @Expose
    private Boolean mPublished;
    @SerializedName("createdAt")
    @Expose
    private String mCreated_at;
    @SerializedName("updatedAt")
    @Expose
    private String mUpdated_at;


    public Post() {
    }

    public Post(String mTitre, String mContenue, List<String> mTags) {
        this.mTitre = mTitre;
        this.mContenue = mContenue;
        this.mTags = mTags;
    }

    /**
     * New Post
     *
     * @param mIdPoster
     * @param mTitre
     * @param mContenue
     * @param mTags
     * @param mPublished
     */
    public Post(String mIdPoster, String mTitre, String mContenue,
                List<String> mTags, Boolean mPublished) {
        this.mIdPoster = mIdPoster;
        this.mTitre = mTitre;
        this.mContenue = mContenue;
        this.mTags = mTags;
        this.mPublished = mPublished;
        this.mCreated_at = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
        this.mUpdated_at = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
    }

    public Post(String mIdPoster, ObjectId mIdPostAsObject, String mIdPost,
                String mTitre, String mContenue, List<String> mTags,
                Boolean mPublished) {
        this.mId = mIdPost;
        this.mIdAsObject = mIdPostAsObject;
        this.mIdPoster = mIdPoster;
        this.mTitre = mTitre;
        this.mContenue = mContenue;
        this.mTags = mTags;
        this.mPublished = mPublished;
    }

    /**
     * Update Post
     *
     * @param mIdPoster
     * @param mIdPostAsObject
     * @param mIdPost
     * @param mTitre
     * @param mContenue
     * @param mTags
     * @param mPublished
     * @param mCreated_at
     */
    public Post(String mIdPoster, ObjectId mIdPostAsObject, String mIdPost,
                String mTitre, String mContenue, List<String> mTags,
                Boolean mPublished, String mCreated_at) {
        this.mId = mIdPost;
        this.mIdAsObject = mIdPostAsObject;
        this.mIdPoster = mIdPoster;
        this.mTitre = mTitre;
        this.mContenue = mContenue;
        this.mTags = mTags;
        this.mPublished = mPublished;
        this.mCreated_at = mCreated_at;
        this.mUpdated_at = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
    }

    public String getTitre() {
        return mTitre;
    }

    public void setTitre(String mTitre) {
        this.mTitre = mTitre;
    }

    public String getContenue() {
        return mContenue;
    }

    public void setContenue(String mContenue) {
        this.mContenue = mContenue;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> mTags) {
        this.mTags = mTags;
    }

    public boolean isTagsEmpty() {
        if (this.mTags == null || this.mTags.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getIdPoster() {
        return mIdPoster;
    }

    public void setIdPoster(String mIdPoster) {
        this.mIdPoster = mIdPoster;
    }

    public Boolean getPublished() {
        return mPublished;
    }

    public void setPublished(Boolean mPublished) {
        this.mPublished = mPublished;
    }

    public String getUpdated_at() {
        return mUpdated_at;
    }

    public void setUpdated_at(String mUpdated_at) {
        this.mUpdated_at = mUpdated_at;
    }

    public String getCreated_at() {
        return mCreated_at;
    }

    public void setCreated_at(String mCreated_at) {
        this.mCreated_at = mCreated_at;
    }

    public Date getDateUpdate() {
        return Date.from(Instant.parse(this.getUpdated_at()));
    }

    public Date getDateCreate() {
        return Date.from(Instant.parse(this.getCreated_at()));
    }

    @Override
    public String toString() {
        return "Post{" +
                ", mIdPoster='" + mIdPoster + '\'' +
                ", mTitre='" + mTitre + '\'' +
                ", mContenue='" + mContenue + '\'' +
                ", mTags=" + mTags +
                ", mPublished=" + mPublished +
                ", mCreated_at='" + mCreated_at + '\'' +
                ", mUpdated_at='" + mUpdated_at + '\'' +
                '}';
    }
}
