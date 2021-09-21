package info.efficacious.centralmodelschool.entity; ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmsTemplatePojo {

    @SerializedName("SMSTemplate")
    @Expose
    private List<SMSTemplate> sMSTemplate = null;

    public List<SMSTemplate> getSMSTemplate() {
        return sMSTemplate;
    }

    public void setSMSTemplate(List<SMSTemplate> sMSTemplate) {
        this.sMSTemplate = sMSTemplate;
    }

}