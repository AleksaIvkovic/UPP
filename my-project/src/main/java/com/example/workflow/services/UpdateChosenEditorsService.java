package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UpdateChosenEditorsService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //ArrayList<User> chosenEditors = (ArrayList<User>)delegateExecution.getVariable("editorsUsers");
        ArrayList<User> remainingEditors = (ArrayList<User>)delegateExecution.getVariable("remainingEditorsUsers");
        ArrayList<User> chosenSubstitutes = (ArrayList<User>)delegateExecution.getVariable("chosenSubstitutes");
        ArrayList<User> haveVoted = (ArrayList<User>)delegateExecution.getVariable("haveVoted");

        ArrayList<User> chosenEditors = new ArrayList<>();
        chosenEditors.addAll(haveVoted);
        chosenEditors.addAll(chosenSubstitutes);

        for (User substitute: chosenSubstitutes) {
            for (User editor: remainingEditors) {
                if (editor.getId().equals(substitute.getId())) {
                    remainingEditors.remove(editor);
                    break;
                }
            }
        }

        ArrayList<String> chosenEditorsUsernames = new ArrayList<String>();

        for(User temp: chosenEditors){
            chosenEditorsUsernames.add(temp.getId());
        }

        delegateExecution.setVariable("remainingEditorsUsers", remainingEditors);
        delegateExecution.setVariable("editorsUsers", chosenEditors);
        delegateExecution.setVariable("editorsUsernames", chosenEditorsUsernames);
    }
}
