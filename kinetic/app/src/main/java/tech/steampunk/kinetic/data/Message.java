package tech.steampunk.kinetic.data;

/**
 * Created by Vamshi on 9/9/2017.
 */

public class Message {

    private String message;
    private String time;
    private String type;

    public Message(String message, String time, String type) {
        this.message = message;
        this.time = time;
        this.type = type;
    }

    public Message(String message, String time) {
        this.message = message;
        this.time = time;
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
