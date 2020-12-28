package com.example.workflow.services;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterToUploadManuscriptService implements JavaDelegate {

    @Autowired
    private MailingService mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We want to inform you that your request for publishing " +
                "of your new book has been approved. Please submit your manuscript.\n\n\t";

        String subject = "Submit manuscript";
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        mailingService.sendMail(subject,text,systemUser.getEmail());
    }
}
