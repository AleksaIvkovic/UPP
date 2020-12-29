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
        ArrayList<User> forbiddenEditors = (ArrayList<User>)delegateExecution.getVariable("forbiddenEditors");
        ArrayList<User> allEditors = (ArrayList<User>)delegateExecution.getVariable("allEditors");

        for (User forbiddenEditor: forbiddenEditors) {
            for (User editor: allEditors) {
                if (forbiddenEditor.getId().toString().equals(editor.getId().toString())) {
                    allEditors.remove(editor);
                    break;
                }
            }
        }

        if (allEditors.size() < 2) {
            allEditors.addAll(forbiddenEditors);
            delegateExecution.setVariable("forbiddenEditors", new ArrayList<User>());
        }

        delegateExecution.setVariable("remainingEditorsUsers", allEditors);
    }
}
