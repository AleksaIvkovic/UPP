package com.example.workflow.services.initialization;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitializeVotesVotersAndNotesService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("editorsNotes", new ArrayList<String>());
        delegateExecution.setVariable("committeeVotes", new ArrayList<String>());
        delegateExecution.setVariable("haveVoted", new ArrayList<User>());
    }
}
