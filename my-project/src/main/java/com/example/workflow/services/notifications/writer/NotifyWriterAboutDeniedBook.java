package com.example.workflow.services.notifications.writer;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutDeniedBook implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String explanationForm = execution.getVariable("explanation").toString();
        String text = "Dear " + execution.getVariable("firstname") +
        ",\n\n\t" + "We are sad to inform you that your request for publishing " +
        "of your new book has been denied. The explanation is given below.\n\n\t" +
        "\"" + explanationForm + "\"";

        String subject = "Suspension notice";
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        mailingService.sendMail(subject,text,systemUser.getEmail());
    }
}
