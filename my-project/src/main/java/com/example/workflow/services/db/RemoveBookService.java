package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveBookService implements JavaDelegate {
    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        bookService.removeBook(execution.getVariable("bookTitle").toString());
    }
}
