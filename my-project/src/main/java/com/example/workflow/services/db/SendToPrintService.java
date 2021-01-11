package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.DBs.PublishedBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Random;

@Service
public class SendToPrintService implements JavaDelegate {
    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        bookService.sendToPrint(delegateExecution.getVariable("bookTitle").toString());
    }
}
