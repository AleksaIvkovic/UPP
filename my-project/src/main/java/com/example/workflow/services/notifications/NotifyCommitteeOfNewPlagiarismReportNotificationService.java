package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotifyCommitteeOfNewPlagiarismReportNotificationService implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> committee = (ArrayList<User>) delegateExecution.getVariable("committee");

        for(User committeeMember: committee){
            String text = "Dear " + committeeMember.getFirstName() +
                    ",\n\n\t" + "You have a new plagiarism report to vote for or against.";

            String subject = "Plagiarism vote";
            mailingService.sendMail(subject,text,committeeMember.getEmail());
        }
    }
}
