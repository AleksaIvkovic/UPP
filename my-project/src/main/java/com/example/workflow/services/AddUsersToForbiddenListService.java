package com.example.workflow.services;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddUsersToForbiddenListService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> chosenEditors = (ArrayList<User>)delegateExecution.getVariable("editorsUsers");
        //ArrayList<SysUser> remainingEditors = (ArrayList<SysUser>)delegateExecution.getVariable("remainingEditorsUsers");
        ArrayList<User> haveVoted = (ArrayList<User>)delegateExecution.getVariable("haveVoted");
        ArrayList<User> forbiddenEditors = (ArrayList<User>)delegateExecution.getVariable("forbiddenEditors");

        chosenEditors.removeAll(haveVoted);
        forbiddenEditors.addAll(chosenEditors);

        delegateExecution.setVariable("forbiddenEditors", forbiddenEditors);
        delegateExecution.setVariable("editorsUsers", chosenEditors);
    }
}
