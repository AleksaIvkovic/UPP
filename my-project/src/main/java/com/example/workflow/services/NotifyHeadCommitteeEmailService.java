package com.example.workflow.services;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class NotifyHeadCommitteeEmailService implements JavaDelegate {

    @Autowired
    IMailing mailingService;

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Writer "  + systemUser.getFirstname() + " " + systemUser.getLastname() +" wants to submit new book. Please review theirs synopsis.";

        ArrayList<User> headCommittee = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("headCommittee").list();
        mailingService.sendMail("New book review",text, headCommittee.get(0).getEmail());
        execution.setVariable("headCommittee", headCommittee.get(0).getId());
    }
}
