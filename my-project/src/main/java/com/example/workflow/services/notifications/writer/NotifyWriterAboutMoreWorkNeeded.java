package com.example.workflow.services.notifications.writer;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class NotifyWriterAboutMoreWorkNeeded implements JavaDelegate {
    @Autowired
    private IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We would kindly ask you to send us more work, " +
                "because committee needs to read more of your work before they make a decision. " +
                "To make it more easy for you, the committee members have left comments: " + "\n\n";

        ArrayList<String> comments = (ArrayList<String>) execution.getVariable("committeeComments");

        for(String comment : comments){
            text += "\t•" + comment + "\n\n";
        }

        String subject = "More work needed";
        mailingService.sendMail(subject, text, execution.getVariable("email").toString());
    }
}
