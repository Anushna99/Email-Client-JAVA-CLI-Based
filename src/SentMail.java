import java.io.Serializable;
import java.time.LocalDate;

public class SentMail implements Serializable{
    private Recipient receiver;
    private String subject;
    private String message;
    private LocalDate sentDate;

    public SentMail(Recipient receiver, String subject, String message, LocalDate sentDate) {
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.sentDate = sentDate;
    }

    public Recipient getRecipient() {
        return this.receiver;
    }

    public LocalDate getSentDate() {
        return this.sentDate;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getMessage() {
        return this.message;
    }
}
