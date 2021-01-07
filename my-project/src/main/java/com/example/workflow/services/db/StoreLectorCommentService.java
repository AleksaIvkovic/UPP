package com.example.workflow.services.db;

import com.example.workflow.models.BookComment;
import com.example.workflow.models.PublishedBook;
import com.example.workflow.models.SysUser;
import com.example.workflow.services.systemServices.BookCommentService;
import com.example.workflow.services.systemServices.BookService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreLectorCommentService implements JavaDelegate {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCommentService bookCommentService;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String comment = delegateExecution.getVariable("lectorNote").toString();
        User lectorUser = (User) delegateExecution.getVariable("lectorUser");
        SysUser lector = systemUserService.getSystemUserByUsername(lectorUser.getId());
        PublishedBook book = bookService.GetBookByTitle(delegateExecution.getVariable("bookTitle").toString());

        BookComment bookComment = new BookComment(comment,book,lector);
        bookCommentService.StoreComment(bookComment);

        List<BookComment> comments = book.getBookComments();
        comments.add(bookComment);
        book.setBookComments(comments);
        bookService.StoreBook(book);
    }
}
