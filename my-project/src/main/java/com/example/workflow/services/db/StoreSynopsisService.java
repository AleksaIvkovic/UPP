package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.intefaces.IGenre;
import com.example.workflow.models.Genre;
import com.example.workflow.models.PublishedBook;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StoreSynopsisService implements JavaDelegate {
    @Autowired
    private IBook bookService;

    @Autowired
    private IGenre genreService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> newPublishedBookForm = (HashMap<String, Object>)execution.getVariable("newPublishedBookForm");

        PublishedBook newPublishedBook = new PublishedBook();
        newPublishedBook.setTitle(newPublishedBookForm.get("title").toString());
        newPublishedBook.setPublished(false);
        newPublishedBook.setGenre(genreService.getGenre(Integer.parseInt(newPublishedBookForm.get("genre").toString())));
        newPublishedBook.setSynopsis(newPublishedBookForm.get("synopsis").toString());
        newPublishedBook.setWriter((SysUser)execution.getVariable("loggedInWriter"));

        bookService.StoreBook(newPublishedBook);
        execution.setVariable("bookTitle", newPublishedBook.getTitle());
    }
}
