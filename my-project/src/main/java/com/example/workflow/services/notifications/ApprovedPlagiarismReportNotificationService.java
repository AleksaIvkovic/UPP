package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovedPlagiarismReportNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser writer = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Dear " + writer.getFirstname() +
                ",\n\n\t" + "We would like to inform you that your plagiarism report for \"" +
                execution.getVariable("title") + "\" by " + execution.getVariable("author") +
                " was approved and that the book was marked as plagiarism.";

        String subject = "Approved plagiarism report";
        mailingService.sendMail(subject, text, writer.getEmail());
    }
}
