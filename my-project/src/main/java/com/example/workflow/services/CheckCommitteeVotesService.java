package com.example.workflow.services;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckCommitteeVotesService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int[] counts =  {0,0,0};
        for(String vote : (ArrayList<String>)delegateExecution.getVariable("committeeVotes")){
            switch (vote){
                case "For_Id": counts[0]++; break;
                case "Against_Id": counts[1]++; break;
                case "NeedMoreWork_Id" : counts[2]++; break;
            }
        }

        if(counts[0] == (int)delegateExecution.getVariable("committeeSize"))
            delegateExecution.setVariable("committeeDecision", "Approved");
        else if(counts[1] >= (int)delegateExecution.getVariable("committeeSize") / 2)
            delegateExecution.setVariable("committeeDecision", "Denied");
        else
            delegateExecution.setVariable("committeeDecision", "NeedMoreWork");
    }
}
