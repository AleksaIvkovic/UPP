package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckNumberOfChosenSubstitutesService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int numOfSubsNeeded = Integer.parseInt(delegateExecution.getVariable("numOfSubsNeeded").toString());
        ArrayList<User> chosenSubstitutes = (ArrayList<User>)delegateExecution.getVariable("chosenSubstitutes");

        if (numOfSubsNeeded != chosenSubstitutes.size()) {
            throw new Exception("Must select ".concat(Integer.toString(numOfSubsNeeded)).concat(" substitutes."));
        }
    }
}
