package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SendSuspensionNoticeEmailService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");

        String text = "Dear " + execution.getVariable("firstname") + ",\n\n\t" + "We are sad to inform you that the request for your membership has been denied.";

        String subject = "Suspension notice";
        mailingService.sendMail(subject,text,execution.getVariable("email").toString());
    }
}
