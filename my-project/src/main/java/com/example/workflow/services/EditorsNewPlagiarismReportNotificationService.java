package com.example.workflow.services;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EditorsNewPlagiarismReportNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<SysUser> editors = (ArrayList<SysUser>)execution.getVariable("editorsUsers");
        String subject = "New plagiarism reported";

        editors.forEach(editor -> {
            String text = "Dear " + editor.getFirstname() +
                    ",\n\n\t" + "There is a new report for possible plagiarism which " +
                    "you should review.";
            mailingService.sendMail(subject, text, editor.getEmail());
        });
    }
}
