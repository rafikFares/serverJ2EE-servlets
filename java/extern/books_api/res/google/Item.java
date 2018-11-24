package extern.books_api.res.google;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("volumeInfo")
    @Expose
    private VolumeInfo volumeInfo;
    @SerializedName("accessInfo")
    @Expose
    private AccessInfo accessInfo;

    public Item(String kind, String id, VolumeInfo volumeInfo, AccessInfo accessInfo) {
        this.kind = kind;
        this.id = id;
        this.volumeInfo = volumeInfo;
        this.accessInfo = accessInfo;
    }

    public Item(VolumeInfo volumeInfo, AccessInfo accessInfo) {
        this.volumeInfo = volumeInfo;
        this.accessInfo = accessInfo;
    }

    public Item() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "kind='" + kind + '\'' +
                ", id='" + id + '\'' +
                ", volumeInfo=" + volumeInfo +
                ", accessInfo=" + accessInfo +
                '}';
    }
}
