package com.example.workflow.services.initialization;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitializationCommentsAndBetaListsService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("comments",new ArrayList<String>());
        execution.setVariable("haveCommented",new ArrayList<User>());
    }
}
