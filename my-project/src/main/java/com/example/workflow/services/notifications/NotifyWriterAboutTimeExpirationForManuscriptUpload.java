package com.example.workflow.services.notifications;

import com.example.workflow.models.SysUser;
import com.example.workflow.services.systemServices.MailingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutTimeExpirationForManuscriptUpload  implements JavaDelegate {

    @Autowired
    private MailingService mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
         String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We are sad to inform you that your time for submitting " +
                 "manuscript has expired. "+ "\"";

        String subject = "Manuscript time expiration";
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        mailingService.sendMail(subject,text,systemUser.getEmail());
    }
}
