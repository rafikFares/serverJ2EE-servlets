package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Comment extends Data{


    @SerializedName("ine")
    @Expose
    private String mIne;
    @SerializedName("id_post")
    @Expose
    private String mIdPost;
    @SerializedName("content")
    @Expose
    private String mContent;
    @SerializedName("createdAt")
    @Expose
    private String mCreated_at;

    public Comment() {
    }

    public Comment(String mIdComent, ObjectId mIdComentAsObject, String mIne,
                   String mIdPost, String mContent) {

        this.mId = mIdComent;
        this.mIdAsObject = mIdComentAsObject;
        this.mIne = mIne;
        this.mIdPost = mIdPost;
        this.mContent = mContent;
    }

    public Comment(String mIne, String mIdPost, String mContent) {
        this.mIne = mIne;
        this.mIdPost = mIdPost;
        this.mContent = mContent;
        this.mCreated_at = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
    }


    public String getIne() {
        return mIne;
    }

    public void setIne(String mIne) {
        this.mIne = mIne;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getIdPost() {
        return mIdPost;
    }

    public void setIdPost(String mIdPost) {
        this.mIdPost = mIdPost;
    }

    public String getCreated_at() {
        return mCreated_at;
    }

    public void setCreated_at(String mCreated_at) {
        this.mCreated_at = mCreated_at;
    }

    public Date getDateCreate() {
        return Date.from(Instant.parse(this.getCreated_at()));
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mIne='" + mIne + '\'' +
                ", mIdPost='" + mIdPost + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mCreated_at='" + mCreated_at + '\'' +
                '}';
    }
}
