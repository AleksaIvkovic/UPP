package com.example.workflow.services.notifications.writer;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NotifyWriterAboutValidPayment implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We would like to inform you that the payment process " +
                "finished successfully and that from this moment on you are full " +
                "member of our Foxy family";

        String subject = "Welcome to the family";
        mailingService.sendMail(subject, text, execution.getVariable("email").toString());
    }
}
