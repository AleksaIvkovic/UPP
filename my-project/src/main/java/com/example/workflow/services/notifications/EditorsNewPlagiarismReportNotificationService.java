package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EditorsNewPlagiarismReportNotificationService implements JavaDelegate {
    @Autowired
    private IMailing mailingService;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<User> editors = (ArrayList<User>)execution.getVariable("editorsUsers");
        String subject = "New plagiarism reported";

        editors.forEach(editor -> {
            String text = "Dear " + editor.getFirstName() +
                    ",\n\n\t" + "There is a new report for possible plagiarism which " +
                    "you should review.";
            mailingService.sendMail(subject, text, editor.getEmail());
        });
    }
}
