package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendVerificationEmailService implements JavaDelegate {

    @Autowired
    private IMailing mailingService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String token = execution.getVariable("verificationToken").toString();
        String processId = execution.getProcessInstanceId();
        String text = "http://localhost:4200/main/email-confirmation/".concat(token).concat("/").concat(processId);

        String subject = "Verification email";
        mailingService.sendMail(subject,text,execution.getVariable("email").toString());
    }
}
