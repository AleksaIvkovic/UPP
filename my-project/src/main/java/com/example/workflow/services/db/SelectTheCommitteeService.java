package com.example.workflow.services.db;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SelectTheCommitteeService implements JavaDelegate {
    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> committee = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("committee").list();
        ArrayList<User> headCommittee = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("headCommittee").list();

        for (User user: headCommittee) {
            committee.add(user);
        }

        ArrayList<String> committeeUsernames = new ArrayList<String>();

        for (User user: committee) {
            committeeUsernames.add(user.getId());
        }

        delegateExecution.setVariable("committeeSize", committee.size());
        delegateExecution.setVariable("committee", committee);
        delegateExecution.setVariable("committeeMembers", committeeUsernames);
    }
}
