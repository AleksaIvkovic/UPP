package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class NotifyHeadEditorAboutNewBookReviewEmailService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");
        User headEditor = (User)execution.getVariable("headEditorUser");

        String text = "Dear " + headEditor.getFirstName() + ",\n\n\t" +
                "Writer " + systemUser.getFirstname() + " " + systemUser.getLastname() +
                " wants to submit new book. Please review theirs synopsis.";

        String subject = "New book review";
        mailingService.sendMail(subject, text, headEditor.getEmail());
    }
}
