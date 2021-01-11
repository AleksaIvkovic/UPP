package com.example.workflow.services.notifications.writer;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NotifyWriterAboutNoMembershipPaymentReceived implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") + ",\n\n\t" +
        "We are sad to inform you that your membership request has been denied due " +
        "to the failed payment of your membership fee.";

        String subject = "Membership denied";
        mailingService.sendMail(subject, text, execution.getVariable("email").toString());
    }
}
