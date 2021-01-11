package com.example.workflow.services.notifications;

import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.MailingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotifyWriterAboutBetaCommentService implements JavaDelegate {
    @Autowired
    private MailingService mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> comments = (ArrayList<String>) execution.getVariable("comments");
        SysUser writer = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + writer.getFirstname() +
                ",\n\n\t" + "We would like to inform you that beta readers have read " +
                "your manuscript. Below are their comments: ";

        for (String comment : comments)
            text = text + "\n\n\t " + comment;

        String subject = "Beta readers comments";
        mailingService.sendMail(subject, text, writer.getEmail());


    }
}
