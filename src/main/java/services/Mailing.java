package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.Properties;

public class Mailing {



    public static Properties getMailtrapProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true); // Use TLS for secure communication
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Replace with your actual hostname if it's different
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.ssl.enable", "false"); // No need for SSL with TLS enabled
        props.put("mail.user", "61d3d9dfcf48e0"); // Replace with your Mailtrap username
        props.put("mail.password", "caffa3fcc9b9f9"); // Replace with your Mailtrap password (avoid storing directly in code)

        return props;
    }
    public static void sendHtmlNotification(String toEmail, String subject, String code , byte[] pdfBytes) {

        Properties props = getMailtrapProperties();


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("contact@gmail.com"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            mimeMessage.setSubject(subject);

            // Create a MimeMultipart to hold both the HTML message and the PDF attachment
            MimeMultipart multipart = new MimeMultipart();

            // Create a MimeBodyPart for the HTML content
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            String htmlContent = "actionNotification.html";
            String html = loadHtmlFromResources(htmlContent);
            html = html.replace("$code", code);
            htmlBodyPart.setContent(html, "text/html");
            multipart.addBodyPart(htmlBodyPart);

            // Create a MimeBodyPart for the PDF attachment
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(pdfBytes, "application/pdf");
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setDisposition(BodyPart.ATTACHMENT);
            attachmentBodyPart.setFileName("facture.pdf"); // Set a descriptive filename
            multipart.addBodyPart(attachmentBodyPart);

            // Set the content of the email to be the MimeMultipart
            mimeMessage.setContent(multipart);

            Transport.send(mimeMessage);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String loadHtmlFromResources(String resourceName) {
        try (InputStream is = Mailing.class.getResourceAsStream("/Mailing/" + resourceName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
