package com.example.Twitter.services;

import com.example.Twitter.exceptions.EmailFailedToSendException;
import com.google.api.services.gmail.Gmail;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Service
public class MailService {

    private final Gmail gmail;

    @Autowired
    public MailService(Gmail gmail){
        this.gmail = gmail;
    }

    public void sendEmail(String toAddress, String subject , String content) throws Exception{
        Properties props = new Properties();

        Session session = Session.getInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        try{
            email.setFrom(new InternetAddress("archanachauhann02@gmail.com"));
            email.addRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));
            email.setSubject(subject);
            email.setText(content);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

            com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
            message.setRaw(encodedEmail);

            message = gmail.users().messages().send("me",message).execute();
        }catch(Exception e){
            throw new EmailFailedToSendException();
        }
    }
}
