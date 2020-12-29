package com.example.workflow.services;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UpdateChosenEditorsService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> chosenEditors = (ArrayList<User>)delegateExecution.getVariable("editorsUsers");
        ArrayList<User> remainingEditors = (ArrayList<User>)delegateExecution.getVariable("remainingEditorsUsers");
        ArrayList<User> chosenSubstitutes = (ArrayList<User>)delegateExecution.getVariable("chosenSubstitutes");

        remainingEditors.removeAll(chosenSubstitutes);
        chosenEditors.addAll(chosenSubstitutes);

        delegateExecution.setVariable("remainingEditorsUsers", remainingEditors);
        delegateExecution.setVariable("editorsUsers", chosenEditors);

        ArrayList<String> chosenEditorsUsernames = new ArrayList<String>();

        for(User temp: chosenEditors){
            chosenEditorsUsernames.add(temp.getId());
        }

        delegateExecution.setVariable("editorsUsernames", chosenEditorsUsernames);
    }
}
