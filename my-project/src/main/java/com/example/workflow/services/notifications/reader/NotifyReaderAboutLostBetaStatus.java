package com.example.workflow.services.notifications.reader;

import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.MailingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotifyReaderAboutLostBetaStatus implements JavaDelegate {
    @Autowired
    private MailingService mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<SysUser> loseBetaStatusReaders = (ArrayList<SysUser>)execution.getVariable("loseBetaStatusReaders");
        for(SysUser user : loseBetaStatusReaders){
            String text = "Dear " + user.getFirstname() +
                    ",\n\n\t" + "We are sad to inform you that you lost beta-reader status " +
                    "because you have 5 penalty points.";

            String subject = "Lost beta-reader status";

            mailingService.sendMail(subject,text,user.getEmail());
        }
    }
}
