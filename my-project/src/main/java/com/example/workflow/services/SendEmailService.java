package com.example.workflow.services;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendEmailService implements JavaDelegate {

    private static final String HOST = "smtp.gmail.com";
    private static final String USER = "literary.assoc@gmail.com";
    private static final String PWD = "UPPpass1";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Email email = new SimpleEmail();
        email.setCharset("utf-8");
        email.setHostName(HOST);
        email.setAuthentication(USER,PWD);
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);

        try {

            email.setFrom("literary.assoc@gmail.com");
            email.setSubject("Verification email");
            email.setMsg("Email message");
            email.addTo(execution.getVariable("email").toString());
            email.send();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
