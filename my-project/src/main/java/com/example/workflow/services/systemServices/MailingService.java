package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IMailing;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

@Service
public class MailingService implements IMailing {

    private static final String HOST = "smtp.gmail.com";
    private static final String USER = "literary.assoc@gmail.com";
    private static final String PWD = "UPPpass1";

    @Override
    public void sendMail(String subject, String text, String receiverEmail) {
        text += "\n\n\tKind regards,\n\tFoxy team";

        Email email = new SimpleEmail();
        email.setCharset("utf-8");
        email.setHostName(HOST);
        email.setAuthentication(USER,PWD);
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);

        try {

            email.setFrom("literary.assoc@gmail.com");
            email.setSubject(subject);
            email.setMsg(text);
            email.addTo(receiverEmail);
            email.send();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
