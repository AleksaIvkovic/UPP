package com.example.workflow.services;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.SysUser;
import com.example.workflow.repositories.SysUserRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
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

        ArrayList<User> users1 = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("committee").list();
        ArrayList<User> users2 = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("headCommittee").list();

        for (User user: users1) {
                mailingService.sendMail(text,user.getEmail());
        }
        for (User user: users2) {
            mailingService.sendMail(text,user.getEmail());
        }

    }
}
