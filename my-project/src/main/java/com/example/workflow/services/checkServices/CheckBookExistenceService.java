package com.example.workflow.services.checkServices;

import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckBookExistenceService implements JavaDelegate {
    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        PublishedBook publishedBook = bookService.getBookByTitle(execution.getVariable("title").toString());
        String fullName = publishedBook.getWriter().getFirstname() + " " + publishedBook.getWriter().getLastname();

        if (publishedBook == null) {
            throw new Exception("Book doesn't exist.");
        } else if (!fullName.equals(execution.getVariable("author").toString())){
            throw new Exception("Authors don't match.");
        }
    }
}
