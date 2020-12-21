package com.example.workflow.services;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class NotifyCommitteeEmailService implements JavaDelegate {

    @Autowired
    IMailing mailingService;

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");

        String text = "New writer " + systemUserForm.get("firstname").toString() + " " + systemUserForm.get("lastname").toString() + " submitted theirs works which you have to review.";

        ArrayList<User> committee = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("committee").list();
        ArrayList<User> headCommittee = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("headCommittee").list();

        for (User user: headCommittee) {
            committee.add(user);
        }

        ArrayList<String> committeeUsernames = new ArrayList<String>();

        for (User user: committee) {
            mailingService.sendMail("New writer review",text,user.getEmail());
            committeeUsernames.add(user.getId());
        }

        execution.setVariable("committeeSize", committee.size());

        execution.setVariable("committeeMembers", committeeUsernames);

    }
}
