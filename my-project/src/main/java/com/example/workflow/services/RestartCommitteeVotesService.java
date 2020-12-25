package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RestartCommitteeVotesService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("committeeVotes", null);
        execution.setVariable("committeeComments", null);
        //Potencijalno stavljanje varijable za inkrementiranje runde na true
    }
}
