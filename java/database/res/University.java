package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;


public class University {

    @SerializedName("_id")
    @Expose
    private ObjectId idUni;
    @SerializedName("uniName")
    @Expose
    private String uniName;
    @SerializedName("portailLink")
    @Expose
    private String portailLink;

    public University(ObjectId idUni, String uniName, String portailLink) {
        this.idUni = idUni;
        this.uniName = uniName;
        this.portailLink = portailLink;
    }

    public University(String uniName, String portailLink) {
        this.uniName = uniName;
        this.portailLink = portailLink;
    }

    public University() {
    }

    public ObjectId getIdUni() {
        return idUni;
    }

    public void setIdUni(ObjectId idUni) {
        this.idUni = idUni;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getPortailLink() {
        return portailLink;
    }

    public void setPortailLink(String portailLink) {
        this.portailLink = portailLink;
    }

    @Override
    public String toString() {
        return "University{" +
                "idUni=" + idUni +
                ", uniName='" + uniName + '\'' +
                ", portailLink='" + portailLink + '\'' +
                '}';
    }
}
