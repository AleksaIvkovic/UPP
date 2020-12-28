package com.example.workflow.services;

import com.example.workflow.models.PublishedBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkBookAsPlagiarizedService implements JavaDelegate {
    @Autowired
    BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        PublishedBook publishedBook = bookService.GetBookByTitle(execution.getVariable("title").toString());
        publishedBook.setPlagiarism(true);
        bookService.StoreBook(publishedBook);
    }
}
