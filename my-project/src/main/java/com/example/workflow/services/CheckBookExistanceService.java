package com.example.workflow.services;

import com.example.workflow.models.PublishedBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckBookExistanceService implements JavaDelegate {
    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        PublishedBook publishedBook = bookService.GetBookByTitle(execution.getVariable("title").toString());
        String fullname = publishedBook.getWriter().getFirstname() + " " + publishedBook.getWriter().getLastname();

        if (publishedBook == null) {
            throw new Exception("Book doesn't exist.");
        } else if (!fullname.equals(execution.getVariable("author").toString())){
            throw new Exception("Authors don't match.");
        }
    }
}
