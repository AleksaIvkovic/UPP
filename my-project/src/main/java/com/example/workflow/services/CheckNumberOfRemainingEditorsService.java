package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckNumberOfRemainingEditorsService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> chosenEditors = (ArrayList<User>)delegateExecution.getVariable("editorsUsers");
        ArrayList<User> remainingEditors = (ArrayList<User>)delegateExecution.getVariable("remainingEditorsUsers");
        ArrayList<User> forbiddenEditors = (ArrayList<User>)delegateExecution.getVariable("forbiddenEditors");
        ArrayList<User> haveVoted = (ArrayList<User>)delegateExecution.getVariable("haveVoted");

        if (remainingEditors.size() < 2) {
            remainingEditors.addAll(forbiddenEditors);
            remainingEditors.addAll(chosenEditors);
            delegateExecution.setVariable("forbiddenEditors", new ArrayList<User>());
        } else {
            remainingEditors.addAll(haveVoted);
        }

        delegateExecution.setVariable("remainingEditorsUsers", remainingEditors);
    }
}
