package com.example.workflow.services.db;

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
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User headEditor = identityService.createUserQuery().memberOfGroup("headEditor").list().get(0);

        delegateExecution.setVariable("headEditorUser", headEditor);
        delegateExecution.setVariable("headEditor", headEditor.getId());
    }
}
