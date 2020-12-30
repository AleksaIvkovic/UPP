package com.example.workflow.services.db;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetLectorService implements JavaDelegate {
    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User lector = identityService.createUserQuery().memberOfGroup("lectors").singleResult();

        delegateExecution.setVariable("lector", lector.getId());
        delegateExecution.setVariable("lectorUser", lector);
    }
}
