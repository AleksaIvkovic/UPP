package com.example.workflow.services;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NotifyWriterOfBookDenialNotification implements JavaDelegate {
    @Autowired
    IMailing mailingService;

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
