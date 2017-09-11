package tech.steampunk.kinetic.data;

/**
 * Created by Vamshi on 9/9/2017.
 */

public class Message {

    private String Message;
    private String Time;
    private String Type;
    private String UID;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Message(String message, String time, String type, String UID) {
        Message = message;
        Time = time;
        Type = type;
        this.UID = UID;
    }

    public Message(String message, String time, String UID) {
        Message = message;
        Time = time;
        this.UID = UID;
    }

    public Message(){

    }
}
