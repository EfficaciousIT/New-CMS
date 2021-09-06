package info.efficacious.centralmodelschool.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessagePojo {

    @SerializedName("SendMessages")
    @Expose
    private List<Message> SendMessages = null;

    public List<Message> getSendMessages() {
        return SendMessages;
    }

    public void setSendMessages(List<Message> sendMessages) {
        SendMessages = sendMessages;
    }
}
