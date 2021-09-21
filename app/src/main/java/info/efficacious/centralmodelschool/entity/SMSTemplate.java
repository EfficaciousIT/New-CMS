package info.efficacious.centralmodelschool.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SMSTemplate {
    @SerializedName("intSMSTemp_id")
    @Expose
    private Integer intSMSTempId;
    @SerializedName("vchTemplate_id")
    @Expose
    private String vchTemplateId;
    @SerializedName("vchTemplate_Name")
    @Expose
    private String vchTemplateName;
    @SerializedName("vchTemplate_Message")
    @Expose
    private String vchTemplate_Message;

    public SMSTemplate() {
    }

    public SMSTemplate(Integer intSMSTempId, String vchTemplateId, String vchTemplateName, String vchTemplate_Message) {
        this.intSMSTempId = intSMSTempId;
        this.vchTemplateId = vchTemplateId;
        this.vchTemplateName = vchTemplateName;
        this.vchTemplate_Message = vchTemplate_Message;
    }

    public Integer getIntSMSTempId() {
        return intSMSTempId;
    }

    public void setIntSMSTempId(Integer intSMSTempId) {
        this.intSMSTempId = intSMSTempId;
    }

    public String getVchTemplateId() {
        return vchTemplateId;
    }

    public void setVchTemplateId(String vchTemplateId) {
        this.vchTemplateId = vchTemplateId;
    }

    public String getVchTemplateName() {
        return vchTemplateName;
    }

    public void setVchTemplateName(String vchTemplateName) {
        this.vchTemplateName = vchTemplateName;
    }

    public String getVchTemplate_Message() {
        return vchTemplate_Message;
    }

    public void setVchTemplate_Message(String vchTemplate_Message) {
        this.vchTemplate_Message = vchTemplate_Message;
    }
}
