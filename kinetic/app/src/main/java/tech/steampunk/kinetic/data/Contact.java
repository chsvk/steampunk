package tech.steampunk.kinetic.data;

/**
 * Created by Vamshi on 9/7/2017.
 */

public class Contact {

    private String name;
    private String number;
    private String message;
    private Boolean constructor;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Contact(){

    }

    public Contact(String name, String message, Boolean constructor){
        this.name = name;
        this.message = message;
        this.constructor = constructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
