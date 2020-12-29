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
        ArrayList<User> haveVoted = (ArrayList<User>)delegateExecution.getVariable("haveVoted");

        chosenEditors = new ArrayList<>();
        chosenEditors.addAll(haveVoted);
        chosenEditors.addAll(chosenSubstitutes);

        for (User substitute: chosenSubstitutes) {
            for (User editor: remainingEditors) {
                if (editor.getId().toString().equals(substitute.getId().toString())) {
                    remainingEditors.remove(editor);
                    break;
                }
            }
        }

        delegateExecution.setVariable("remainingEditorsUsers", remainingEditors);
        delegateExecution.setVariable("editorsUsers", chosenEditors);

        ArrayList<String> chosenEditorsUsernames = new ArrayList<String>();

        for(User temp: chosenEditors){
            chosenEditorsUsernames.add(temp.getId());
        }

        delegateExecution.setVariable("editorsUsernames", chosenEditorsUsernames);
    }
}
