package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Interest extends Data{


    @SerializedName("ine")
    @Expose
    private String mIne;
    @SerializedName("preference")
    @Expose
    private String mPreference;

    public Interest() {
    }

    public Interest(String mIne, String mInterests) {
        this.mIne = mIne;
        this.mPreference = mInterests;
    }

    public String getIne() {
        return mIne;
    }

    public void setIne(String mIne) {
        this.mIne = mIne;
    }

    public String getInterest() {
        return mPreference;
    }

    public void setInterest(String mInterests) {
        this.mPreference = mInterests;
    }


    @Override
    public String toString() {
        return "Interest{" +
                ", mIne='" + mIne + '\'' +
                ", mInterests=" + mPreference +
                '}';
    }
}
