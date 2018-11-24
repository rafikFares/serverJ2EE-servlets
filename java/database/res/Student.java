package database.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("ine")
    @Expose
    private String mIne;
    @SerializedName("matricule")
    @Expose
    private Integer mMatricule;
    @SerializedName("username")
    @Expose
    private String mUserName;
    @SerializedName("password")
    @Expose
    private String mPass;
    @SerializedName("firstname")
    @Expose
    private String mFirstName;
    @SerializedName("lastname")
    @Expose
    private String mLastName;
    @SerializedName("datebirth")
    @Expose
    private String mDateOfBirth;
    @SerializedName("placebirth")
    @Expose
    private String mPlaceOfBirth;

    @SerializedName("token")
    @Expose
    private String mToken;


    @SerializedName("email")
    @Expose
    private String mEmail;

    public Student() {
    }

    public Student(String mIne, Integer mMatricule, String mUserName, String mPass,
                   String mFirstName, String mLastName, String mDateOfBirth,
                   String mPlaceOfBirth,String mEmail) {

        this.mIne = mIne;
        this.mMatricule = mMatricule;
        this.mUserName = mUserName;
        this.mPass = mPass;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mDateOfBirth = mDateOfBirth;
        this.mPlaceOfBirth = mPlaceOfBirth;
    }

    public String getIne() {
        return mIne;
    }

    public void setIne(String mIne) {
        this.mIne = mIne;
    }

    public Integer getMatricule() {
        return mMatricule;
    }

    public void setMatricule(Integer mMatricule) {
        this.mMatricule = mMatricule;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String mPass) {
        this.mPass = mPass;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String mPlaceOfBirth) {
        this.mPlaceOfBirth = mPlaceOfBirth;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    @Override
    public String toString() {
        return "Student{" +
                "mIne='" + mIne + '\'' +
                ", mMatricule=" + mMatricule +
                ", mUserName='" + mUserName + '\'' +
                ", mPass='" + mPass + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mDateOfBirth='" + mDateOfBirth + '\'' +
                ", mPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
