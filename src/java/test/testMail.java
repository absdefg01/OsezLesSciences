package test;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class testMail {
    public void send(String mail) throws Exception {
        /* L'adresse IP de votre serveur SMTP */
        String smtpServer = "10.48.000.00";

        /* L'adresse de l'expéditeur */
        String from = "monAdresse@monDomaine.fr";

        /* L'adresse du destinataire */
        String to = mail;

        /* L'objet du message */
        String objet = "Objet";

        /* Le corps du mail */
        String texte = "Texte du mail";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);
        /* Session encapsule pour un client donné sa connexion avec le serveur de mails.*/
        Session session = Session.getDefaultInstance(props, null);

        /* Création du message*/
        Message msg = new MimeMessage(session);

        try {
              msg.setFrom(new InternetAddress(from));
              msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
              msg.setSubject(objet);
              msg.setText(texte);
              msg.setHeader("X-Mailer", "LOTONtechEmail");
              Transport.send(msg);
        }
        catch (AddressException e) {
              e.printStackTrace();
        } 
        catch (MessagingException e) {
              e.printStackTrace();
        }
    }
}