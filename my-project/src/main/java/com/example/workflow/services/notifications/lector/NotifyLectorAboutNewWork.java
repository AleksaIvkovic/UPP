package com.example.workflow.services.notifications.lector;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyLectorAboutNewWork implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User lector = (User)delegateExecution.getVariable("lectorUser");

        String text = "Dear " + lector.getFirstName() + ",\n\n\t" +
                "You have new work to lector.";

        String subject = "New work to lector";
        mailingService.sendMail(subject, text, lector.getEmail());
    }
}
