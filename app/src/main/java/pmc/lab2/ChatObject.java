package pmc.lab2;

import java.io.Serializable;

/**
 * Created by pmc on 9/23/14.
 */
public class ChatObject implements Serializable{
    String sender, message, timestamp;

    public ChatObject (String sender, String message, String timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}


