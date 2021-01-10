package com.example.workflow.services.initialization;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitializeForbiddenAndRemainingEditorsService implements JavaDelegate {
    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("forbiddenEditors", new ArrayList<User>());

        ArrayList<User> editors = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("editors").list();
        delegateExecution.setVariable("remainingEditorsUsers", editors);
        delegateExecution.setVariable("allEditors", editors);
    }
}
