package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class InvalidPaymentNotificationService implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We are sorry to inform you that something went wrong " +
                "during the payment process and that you need to fill out the form again." +
                "\n\n\tSorry for the inconvenience,\n\n\tYour Foxy team";

        String subject = "Invalid payment";
        mailingService.sendMail(subject, text, execution.getVariable("email").toString());
    }
}
