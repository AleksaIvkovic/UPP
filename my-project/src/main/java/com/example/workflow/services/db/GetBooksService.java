package com.example.workflow.services.db;

import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetBooksService implements JavaDelegate {
    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<PublishedBook> books = new ArrayList<>();
        books.add(bookService.getBookByTitle(delegateExecution.getVariable("myTitle").toString()));
        books.add(bookService.getBookByTitle(delegateExecution.getVariable("title").toString()));

        delegateExecution.setVariable("booksToReview", books);
    }
}
