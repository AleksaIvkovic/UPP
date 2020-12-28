package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckCommitteePlagiarismVoteService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int[] counts =  {0,0};

        for(String vote : (ArrayList<String>)delegateExecution.getVariable("committeeVotes")){
            switch (vote){
                case "IsPlagiarism_Id": counts[0]++; break;
                case "IsNotPlagiarism_Id": counts[1]++; break;
            }
        }

        if(counts[0] == (int)delegateExecution.getVariable("committeeSize"))
            delegateExecution.setVariable("isPlag", "True");
        else if(counts[1] == (int)delegateExecution.getVariable("committeeSize"))
            delegateExecution.setVariable("isPlag", "False");
        else
            delegateExecution.setVariable("isPlag", "VoteAgain");
    }
}
