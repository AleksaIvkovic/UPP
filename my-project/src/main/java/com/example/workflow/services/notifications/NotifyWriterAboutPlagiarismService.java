package com.example.workflow.services.notifications;

import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.MailingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutPlagiarismService  implements JavaDelegate {
    @Autowired
    private MailingService mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We are sad to inform you that your manuscript has been denied " +
                "because there are suspicions that it is plagiarism.";

        String subject = "Manuscript denied";
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        mailingService.sendMail(subject,text,systemUser.getEmail());
    }
}
