package info.efficacious.centralmodelschool.entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentStandardwiseDetail implements Serializable {

    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SerializedName("Student_id")
    @Expose
    private Integer studentId;
    @SerializedName("intSchool_id")
    @Expose
    private Integer intSchoolId;
    @SerializedName("vchProfile")
    @Expose
    private String vchProfile;
    @SerializedName("Standard_name")
    @Expose
    private String standardName;
    @SerializedName("Division_name")
    @Expose
    private String divisionName;
    @SerializedName("Standard_id")
    @Expose
    private Integer standardId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Division_id")
    @Expose
    private Integer divisionId;

    @SerializedName("Mobile_number")
    @Expose
    private String Mobile_number;

    public String getMobile_number() {
        return Mobile_number;
    }

    public void setMobile_number(String  mobile_number) {
        Mobile_number = mobile_number;
    }



    protected StudentStandardwiseDetail(Parcel in) {
        if (in.readByte() == 0) {
            studentId = null;
        } else {
            studentId = in.readInt();
        }
        if (in.readByte() == 0) {
            intSchoolId = null;
        } else {
            intSchoolId = in.readInt();
        }
        vchProfile = in.readString();
        standardName = in.readString();
        divisionName = in.readString();
        if (in.readByte() == 0) {
            standardId = null;
        } else {
            standardId = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            divisionId = null;
        } else {
            divisionId = in.readInt();
        }
    }



    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getIntSchoolId() {
        return intSchoolId;
    }

    public void setIntSchoolId(Integer intSchoolId) {
        this.intSchoolId = intSchoolId;
    }

    public String getVchProfile() {
        return vchProfile;
    }

    public void setVchProfile(String vchProfile) {
        this.vchProfile = vchProfile;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    @Override
    public String toString() {
        return "StudentStandardwiseDetail{" +
                "isSelected=" + isSelected +
                ", studentId=" + studentId +
                ", intSchoolId=" + intSchoolId +
                ", vchProfile='" + vchProfile + '\'' +
                ", standardName='" + standardName + '\'' +
                ", divisionName='" + divisionName + '\'' +
                ", standardId=" + standardId +
                ", name='" + name + '\'' +
                ", divisionId=" + divisionId +
                ", Mobile_number='" + Mobile_number + '\'' +
                '}';
    }
}