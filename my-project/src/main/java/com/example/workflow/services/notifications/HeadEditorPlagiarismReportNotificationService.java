package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class HeadEditorPlagiarismReportNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");
        User headEditor = (User)execution.getVariable("headEditorUser");

        String text = "Dear " + headEditor.getFirstName() + ",\n\n\t" +
                "There has been report of a possible plagiarized book and you need to" +
                "select editors to check if it is plagiarized.";

        String subject = "Plagiarism report";
        mailingService.sendMail(subject, text, headEditor.getEmail());
    }
}
