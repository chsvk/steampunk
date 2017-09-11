package tech.steampunk.kinetic.data;

/**
 * Created by Vamshi on 9/9/2017.
 */

public class Message {

    private String message;
    private String time;
    private String type;
    private String uid;

    public Message(String message, String time, String type, String uid) {
        this.message = message;
        this.time = time;
        this.type = type;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Message(String message, String time, String uid) {

        this.message = message;
        this.time = time;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
