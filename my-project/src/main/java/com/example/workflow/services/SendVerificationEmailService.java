package com.example.workflow.services;

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
    IMailing mailingService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String token = execution.getVariable("verificationToken").toString();
        String processId = execution.getProcessInstanceId();
        String text = "http://localhost:4200/main/email-confirmation/".concat(token).concat("/").concat(processId);

        mailingService.sendMail(text,execution.getVariable("email").toString());
    }
}
