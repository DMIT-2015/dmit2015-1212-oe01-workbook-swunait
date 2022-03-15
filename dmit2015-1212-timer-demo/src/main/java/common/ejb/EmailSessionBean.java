package common.ejb;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;

/**
 * To send email with the Wildfly mail subsystem you must first configure the default mail session with your own email account as follows:
 * Step 1: Start the JBoss WildFly server
 * Step 2: Open a Terminal window and type in the following commands to configure the mail subsystem with your own email account info.

 $JBOSS_HOME/bin/jboss-cli.sh
 connect
 batch
 /subsystem=mail/mail-session=default/server=smtp/:write-attribute(name=username,value=yourGmailUsername@gmail.com)

 /subsystem=mail/mail-session=default/server=smtp/:write-attribute(name=password,value=yourGmailAppPassword)

 /subsystem=mail/mail-session=default/server=smtp/:write-attribute(name=tls,value=true)

 /subsystem=mail/mail-session=default/:write-attribute(name=from,value=yourGmailUsername@gmail.com)

 /socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=mail-smtp/:write-attribute(name=host,value=smtp.gmail.com)

 /socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=mail-smtp/:write-attribute(name=port,value=587)

 run-batch
 reload

 *
 * To use this session session from another managed class, simply use the @Inject annotation to inject an instance of this bean:
 *
 *  @Inject
 *  private EmailSessionBean _emailSessionBean;
 *
 */
@Stateless
public class EmailSessionBean {

        @Resource(name = "java:jboss/mail/Default")
        private Session _defaultSession;

        @PostConstruct
        public void init() {
                // For Outlook email you need to enable starttls
//                _defaultSession.getProperties().setProperty("mail.smtp.starttls.enable","true");
        }

        @Asynchronous
        public void sendTextEmail(String mailToAddresses, String mailSubject, String mailBody) throws MessagingException {
                Message mailMessage = new MimeMessage(_defaultSession);
                mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToAddresses));
                mailMessage.setSubject(mailSubject);
                mailMessage.setText(mailBody);
                Transport.send(mailMessage);
        }

        @Asynchronous
        public void sendHtmlEmail(String mailToAddresses, String mailSubject, String mailBody) throws MessagingException {
                Message mailMessage = new MimeMessage(_defaultSession);
                mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToAddresses));
                mailMessage.setSubject(mailSubject);
                Multipart multipart = new MimeMultipart();
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(mailBody,"text/html");
                multipart.addBodyPart(htmlPart);
                mailMessage.setContent(multipart);
                Transport.send(mailMessage);
        }

        @Asynchronous
        public void sendHtmlEmailWithAttachments(String mailToAddresses, String mailSubject, String mailBody, String[] files) throws MessagingException {
                Message mailMessage = new MimeMessage(_defaultSession);
                mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToAddresses));
                mailMessage.setSubject(mailSubject);
                Multipart multipart = new MimeMultipart();
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(mailBody,"text/html");
                multipart.addBodyPart(htmlPart);

                for (String filename : files) {
                        if (!filename.isBlank()) {
                                MimeBodyPart filePart = new MimeBodyPart();
                                DataSource fileSource = new FileDataSource(filename);
                                filePart.setDataHandler(new DataHandler(fileSource));
                                File file = new File(filename);
                                filePart.setFileName(file.getName());
                                multipart.addBodyPart(filePart);
                        }
                }

                mailMessage.setContent(multipart);
                Transport.send(mailMessage);
        }

}