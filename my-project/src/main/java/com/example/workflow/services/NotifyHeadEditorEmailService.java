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
public class NotifyHeadEditorEmailService implements JavaDelegate {

    @Autowired
    IMailing mailingService;

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        SysUser systemUser = (SysUser)execution.getVariable("loggedInWriter");

        String text = "Writer "  + systemUser.getFirstname() + " " + systemUser.getLastname() +" wants to submit new book. Please review theirs synopsis.";

        ArrayList<User> headEditor = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("headEditor").list();
        mailingService.sendMail("New book review",text, headEditor.get(0).getEmail());
        execution.setVariable("headEditor", headEditor.get(0).getId());
    }
}
