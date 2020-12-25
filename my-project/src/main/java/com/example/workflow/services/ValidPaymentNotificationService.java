package com.example.workflow.services;

import com.example.workflow.intefaces.IMailing;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class ValidPaymentNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");

        String text = "Dear " + execution.getVariable("firstname") +
                ",\n\n\t" + "We would like to inform you that the payment process " +
                "finished successfully and that from this moment on you are full " +
                "member of our Foxy family";
        //Dodati nesto u fazonu:"We are looking forward to working with you, all the best"

        String subject = "Welcome to the family";
        mailingService.sendMail(subject, text, execution.getVariable("email").toString());
    }
}
