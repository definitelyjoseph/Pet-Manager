import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailAPI {

private final String senderEmail;
private final String password;
private final Properties props;

public MailAPI(String senderEmail, String password){
  this.senderEmail = senderEmail;
  this.password = password;

  props = new Properties();
  props.put("mail.smtp.auth", "true");
  props.put("mail.smtp.starttls.enable", "true");
  props.put("mail.smtp.host", "smtp.gmail.com");
  props.put("mail.smtp.port", "587");
}

  public void sendMail(String toEmail, String subject, String body){
    Session session = Session.getInstance(props, new Authenticator(){
      protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(sendEmail, password);
      }
    });

    try{
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(senderEmail));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
      message.setSubject(subject);
      message.setText(body);

      Transport.send(message);
      System.out.println("Mail sent successfully to " + toEmail);
    } catch (MessageException e) {
      System.err.println("Error sending email: " + e.getMessage());
    }
  }
}


  
