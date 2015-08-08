package rocks.gdgmaceio.firedroid.model;

/**
 * Created by bruno on 07/08/15.
 */
public class Message {
    public String message;
    public String author;

    public Message() {
    }

    public Message(String message, String author) {
        this.message = message;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
