package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotifyHeadEditorOfSubstitutionChoiceNeededNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User headEditor = (User) delegateExecution.getVariable("headEditorUser");

        String text = "Dear " + headEditor.getFirstName() +
                ",\n\n\t" + "You have to choose "
                + delegateExecution.getVariable("numOfSubsNeeded").toString() +
                " new editors as substitutes.";

        String subject = "Substitutes selection";
        mailingService.sendMail(subject,text,headEditor.getEmail());
    }
}
