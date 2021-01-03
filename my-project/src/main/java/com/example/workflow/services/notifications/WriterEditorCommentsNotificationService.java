package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterEditorCommentsNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + systemUser.getFirstname() +
                ",\n\n\tHead editor has reviewed your book and left you some comments and " +
                "suggestions. Please consider altering your work based on those comments and " +
                "submit it once you are finished.\n\n" +
                "Comment:\n\t";
        text += execution.getVariable("editorComment").toString();

        String subject = "Editor's comments";
        mailingService.sendMail(subject, text, systemUser.getEmail());
    }
}
