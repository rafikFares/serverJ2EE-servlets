package extern.books_api.res.google;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessInfo {

    @SerializedName("webReaderLink")
    @Expose
    private String webReaderLink;


    public AccessInfo(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public AccessInfo() {
    }


    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    @Override
    public String toString() {
        return "AccessInfo{" +
                "webReaderLink='" + webReaderLink + '\'' +
                '}';
    }
}