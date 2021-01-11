package com.example.workflow.services.notifications.writer;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutMoreWorkNeededRequestByEditor implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser writer = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + writer.getFirstname() +
                ",\n\n\t" + "We would like to inform you that head editor has requested that you " +
                "submit updated manuscript.";

        String subject = "Updated manuscript needed";
        mailingService.sendMail(subject, text, writer.getEmail());
    }
}
