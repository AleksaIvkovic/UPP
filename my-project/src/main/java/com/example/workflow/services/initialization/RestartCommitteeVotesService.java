package com.example.workflow.services.initialization;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RestartCommitteeVotesService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("committeeVotes", new ArrayList<String>());
        execution.setVariable("committeeComments", new ArrayList<String>());
        //Potencijalno stavljanje varijable za inkrementiranje runde na true
    }
}
