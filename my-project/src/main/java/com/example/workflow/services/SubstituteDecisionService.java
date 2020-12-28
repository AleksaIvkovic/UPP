package com.example.workflow.services;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubstituteDecisionService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<User> chosenEditors = (ArrayList<User>)delegateExecution.getVariable("editorsUsers");
        ArrayList<User> remainingEditors = (ArrayList<User>)delegateExecution.getVariable("remainingEditorsUsers");
        ArrayList<User> haveVoted = (ArrayList<User>)delegateExecution.getVariable("haveVoted");

        int didNotVote = chosenEditors.size() - haveVoted.size();
        if (remainingEditors.size() == 0) {
            delegateExecution.setVariable("nextStep", "Committee");
        } else {
            if (remainingEditors.size() <= didNotVote) {
                chosenEditors = haveVoted;
                chosenEditors.addAll(remainingEditors);
                delegateExecution.setVariable("nextStep", "Editors");
                delegateExecution.setVariable("remainingEditorsUsers", new ArrayList<User>());
                delegateExecution.setVariable("editorsUsers", chosenEditors);
            } else {
                delegateExecution.setVariable("nextStep", "Substitutions");
                delegateExecution.setVariable("numOfSubsNeeded", didNotVote);
            }
        }
    }
}
