package com.example.workflow.services.db;

import com.example.workflow.helpers.TempHelper;
import com.example.workflow.services.systemServices.BookCommentService;
import com.example.workflow.services.systemServices.BookService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreEditorCommentService implements JavaDelegate {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCommentService bookCommentService;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String comment = delegateExecution.getVariable("editorComment").toString();
        User user = (User)delegateExecution.getVariable("headEditorUser");
        String title = delegateExecution.getVariable("bookTitle").toString();

        /*PublishedBook book = bookService.getBookByTitle(delegateExecution.getVariable("bookTitle").toString());
        SysUser sysUser = systemUserService.getSystemUserByUsername(user.getId());
        BookComment bookComment = new BookComment(comment, book, sysUser);
        bookCommentService.StoreComment(bookComment);
        bookService.storeComment(book, bookComment);*/
        TempHelper.storeComment(comment, user, bookService, title, systemUserService, bookCommentService);
    }
}
