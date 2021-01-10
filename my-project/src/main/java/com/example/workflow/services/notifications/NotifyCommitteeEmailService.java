package com.example.workflow.services.notifications;

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
    private IMailing mailingService;

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");

        String text = "New writer " + systemUserForm.get("firstname").toString() + " " + systemUserForm.get("lastname").toString() + " submitted theirs works which you have to review.";

        ArrayList<User> committee = (ArrayList<User>)execution.getVariable("committee");

        for (User user: committee) {
            mailingService.sendMail("New writer review",text,user.getEmail());
        }
    }
}
