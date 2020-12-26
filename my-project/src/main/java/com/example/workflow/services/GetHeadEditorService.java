package com.example.workflow.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetHeadEditorService implements JavaDelegate {
    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User headEditor = (User) identityService.createUserQuery().memberOfGroup("headEditor").list().get(0);

        delegateExecution.setVariable("headEditorUser", headEditor);
        delegateExecution.setVariable("headEditor", headEditor.getId());
    }
}
