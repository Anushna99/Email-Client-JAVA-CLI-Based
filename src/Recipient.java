import java.io.Serializable;


public abstract class Recipient implements Serializable {
    transient private static int recipient_count = 0;
    private String name;
    private String email_address;

    public Recipient(String name, String email_address) {
        ++recipient_count;
        this.name = name;
        this.email_address = email_address;
    }

    public void sendMail(String subject, String message) {
        // sending mail here
        try {
            Mailer.send(this.email_address, subject, message);
            System.out.println("\nMessage sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRecipientCount() {
        return recipient_count;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email_address;
    }

}
