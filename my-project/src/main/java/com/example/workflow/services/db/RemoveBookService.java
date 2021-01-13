package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.intefaces.IBookComment;
import com.example.workflow.models.DBs.PublishedBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveBookService implements JavaDelegate {
    @Autowired
    private IBook bookService;
    @Autowired
    private IBookComment bookCommentService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        PublishedBook bookToDelete = bookService.getBookByTitle(execution.getVariable("bookTitle").toString());
        bookCommentService.deleteCommentsForBook(bookToDelete.getId());
        bookService.removeBook(bookToDelete);
    }
}
