package com.example.workflow.services;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.PublishedBook;
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
        //PublishedBook publishedBook = bookService.GetBookByTitle(execution.getVariable("bookTitle").toString());
        bookService.RemoveBook(bookService.GetBookByTitle(execution.getVariable("bookTitle").toString()));
    }
}
