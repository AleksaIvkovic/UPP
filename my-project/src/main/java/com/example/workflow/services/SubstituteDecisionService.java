package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubstituteDecisionService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> chosenEditors = (ArrayList<User>)delegateExecution.getVariable("editorsUsers");
        ArrayList<User> remainingEditors = (ArrayList<User>)delegateExecution.getVariable("remainingEditorsUsers");
        ArrayList<User> haveVoted = (ArrayList<User>)delegateExecution.getVariable("haveVoted");
        ArrayList<User> forbiddenEditors = (ArrayList<User>)delegateExecution.getVariable("forbiddenEditors");

        int didNotVote = chosenEditors.size() - haveVoted.size();
        for (User votedEditor: haveVoted) {
            for (User editor: chosenEditors) {
                if (editor.getId().toString().equals(votedEditor.getId().toString())) {
                    chosenEditors.remove(editor);
                    break;
                }
            }
        }
        forbiddenEditors.addAll(chosenEditors);
        delegateExecution.setVariable("forbiddenEditors", forbiddenEditors);

        if (remainingEditors.size() == 0) {
            delegateExecution.setVariable("nextStep", "Committee");
        } else {
            if (remainingEditors.size() <= didNotVote) {
                delegateExecution.setVariable("nextStep", "Editors");
                chosenEditors = new ArrayList<User>();
                chosenEditors.addAll(haveVoted);
                chosenEditors.addAll(remainingEditors);
                delegateExecution.setVariable("remainingEditorsUsers", new ArrayList<User>());
                delegateExecution.setVariable("remainingEditorsUsernames", new ArrayList<String>());
                delegateExecution.setVariable("editorsUsers", chosenEditors);
                ArrayList<String> editorUsernames = new ArrayList<>();
                chosenEditors.forEach(editor -> {
                    editorUsernames.add(editor.getId().toString());
                });
                delegateExecution.setVariable("editorsUsernames", editorUsernames);
            } else {
                delegateExecution.setVariable("nextStep", "Substitutions");
                delegateExecution.setVariable("numOfSubsNeeded", didNotVote);
            }
        }
    }
}
