package info.efficacious.centralmodelschool.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDetail {
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public String intMobileNo;

    public String getIntMobileNo() {
        return intMobileNo;
    }

    public void setIntMobileNo(String intMobileNo) {
        this.intMobileNo = intMobileNo;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SerializedName("Teacher_id")
    @Expose
    private Integer teacherId;
    @SerializedName("vchProfile")
    @Expose
    private String vchProfile;
    @SerializedName("Department_name")
    @Expose
    private String departmentName;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("NamewithDesig")
    @Expose
    private String namewithDesig;
    @SerializedName("intSchool_id")
    @Expose
    private Integer intSchoolId;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getVchProfile() {
        return vchProfile;
    }

    public void setVchProfile(String vchProfile) {
        this.vchProfile = vchProfile;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamewithDesig() {
        return namewithDesig;
    }

    public void setNamewithDesig(String namewithDesig) {
        this.namewithDesig = namewithDesig;
    }

    public Integer getIntSchoolId() {
        return intSchoolId;
    }

    public void setIntSchoolId(Integer intSchoolId) {
        this.intSchoolId = intSchoolId;
    }

    @Override
    public String toString() {
        return "TeacherDetail{" +
                "isSelected=" + isSelected +
                ", intMobileNo='" + intMobileNo + '\'' +
                ", teacherId=" + teacherId +
                ", vchProfile='" + vchProfile + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", designation='" + designation + '\'' +
                ", name='" + name + '\'' +
                ", namewithDesig='" + namewithDesig + '\'' +
                ", intSchoolId=" + intSchoolId +
                '}';
    }
}