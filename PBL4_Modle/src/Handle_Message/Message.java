package Handle_Message;

public class Message {
private String message;
    
    public Message() {
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isMessage(String message) {
        return this.message.equals(message);
    }
    public String toString() {
        return String.format("msg: %s", getMessage());
    }
}
