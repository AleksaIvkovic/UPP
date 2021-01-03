package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyEditorOfUpdatedManuscript implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User headEditor = (User)delegateExecution.getVariable("headEditorUser");

        String text = "Dear " + headEditor.getFirstName() + ",\n\n\t" +
                "Writer updated theirs manuscript that you need to review.";

        String subject = "Updated manuscript";
        mailingService.sendMail(subject, text, headEditor.getEmail());
    }
}