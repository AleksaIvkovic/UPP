package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterLectorsNoteNotificationService implements JavaDelegate {

    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + systemUser.getFirstname() +
                ",\n\n\t" + "Lector has revied your book and left you a note, after you alter your work," +
                "you can submit it again.\n\nNote:\n\t";
        text += execution.getVariable("lectorNote").toString();

        String subject = "Lector's note";

        mailingService.sendMail(subject,text,systemUser.getEmail());
    }
}
