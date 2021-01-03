package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterSuccessfulBookPublishNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + systemUser.getFirstname() +
                ",\n\n\tWe are happy to inform you that your book has been reviewed and checked by " +
                "our team and beta readers and that it has been approved and sent to the printing " +
                "station.";

        String subject = "Congratulations";
        mailingService.sendMail(subject, text, systemUser.getEmail());
    }
}
