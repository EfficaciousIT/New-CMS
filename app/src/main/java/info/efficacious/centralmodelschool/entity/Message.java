package info.efficacious.centralmodelschool.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message  {
    @SerializedName("intStudent_id")
    @Expose
    private Integer intStudent_id;

    @SerializedName("Student_Name")
    @Expose
    private String Student_Name;

    @SerializedName("intBusAlert1")
    @Expose
    private String intBusAlert1;

    public Integer getIntStudent_id() {
        return intStudent_id;
    }

    public void setIntStudent_id(Integer intStudent_id) {
        this.intStudent_id = intStudent_id;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getIntBusAlert1() {
        return intBusAlert1;
    }

    public void setIntBusAlert1(String intBusAlert1) {
        this.intBusAlert1 = intBusAlert1;
    }

    public Message(Integer intStudent_id, String student_Name, String intBusAlert1) {
        this.intStudent_id = intStudent_id;
        Student_Name = student_Name;
        this.intBusAlert1 = intBusAlert1;
    }
}
