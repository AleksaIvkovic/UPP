package com.example.workflow.services.notifications;

import com.example.workflow.intefaces.IMailing;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMarkedAsPlagiarismNotificationService implements JavaDelegate {
    @Autowired
    IMailing mailingService;

    @Autowired
    BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        PublishedBook book = (PublishedBook)bookService.GetBookByTitle(execution.getVariable("title").toString());

        String text = "Dear " + book.getWriter().getFirstname() +
                ",\n\n\t" + "We would like to inform you that your book \"" +
                book.getTitle() + "\" was marked as plagiarism.";

        String subject = "Book marked as plagiarism";
        mailingService.sendMail(subject, text, book.getWriter().getEmail());
    }
}
