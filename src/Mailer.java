import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {
    private static String signature = "\n\n\nBest Regards\n" + "Anushna Jayathunga";
    private static String myMail = "ashashipriyajayathunga@gmail.com";
    private static String password = "glnatntuvmtkkzxt";

    public static void send(String to, String sub, String msg) throws RuntimeException {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        // get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myMail, password);
                    }
                });
        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg + signature);
            // send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
