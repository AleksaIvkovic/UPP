package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class IncrementRoundService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int round = Integer.parseInt(delegateExecution.getVariable("Round").toString());
        delegateExecution.setVariable("Round", round + 1);
    }
}
