package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PayMembershipNotificationService implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") +
        ",\n\n\t" + "We would kindly ask you to pay your membership fee so the " +
        "process of your registration could be completed successfully.";

        String subject = "Membership payment";
        mailingService.sendMail(subject, text, execution.getVariable("email").toString());
    }
}
