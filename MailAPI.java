import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * The {@code MailAPI} class provides functionality for sending emails using the SMTP protocol.
 * It is designed to handle email communication in the pet adoption system, such as sending notifications
 * to customers or admins.
 * 
 * <p>This class uses the JavaMail API to configure and send emails through an SMTP server.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Configuring SMTP server properties</li>
 *   <li>Authenticating the sender's email credentials</li>
 *   <li>Sending emails with a subject and body to specified recipients</li>
 * </ul>
 */
public class MailAPI {

    private final String senderEmail;
    private final String password;
    private final Properties props;

    /**
     * Constructs a new {@code MailAPI} object with the specified sender email and password.
     * Initializes the SMTP server properties for sending emails.
     *
     * @param senderEmail the email address of the sender
     * @param password the password for the sender's email account
     */
    public MailAPI(String senderEmail, String password) {
        this.senderEmail = senderEmail;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    /**
     * Sends an email to the specified recipient with the given subject and body.
     *
     * @param toEmail the recipient's email address
     * @param subject the subject of the email
     * @param body the body content of the email
     */
    public void sendMail(String toEmail, String subject, String body) {
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Mail sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}



