package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorPossibleFinishedBookNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        User headEditor = (User)execution.getVariable("headEditorUser");
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + headEditor.getFirstName() + ",\n\n\t" +
                "Book by " + systemUser.getFirstname() + " " + systemUser.getLastname() +
                " has reached final stage before publishing. You need to submit final decision.";

        String subject = "Possible finished book";
        mailingService.sendMail(subject, text, headEditor.getEmail());
    }
}
