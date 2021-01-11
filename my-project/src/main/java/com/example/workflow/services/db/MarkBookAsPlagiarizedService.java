package com.example.workflow.services.db;

import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkBookAsPlagiarizedService implements JavaDelegate {
    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        bookService.markAsPlagiarized(execution.getVariable("title").toString());
    }
}
