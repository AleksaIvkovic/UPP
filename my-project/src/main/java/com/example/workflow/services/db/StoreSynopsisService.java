package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.intefaces.IGenre;
import com.example.workflow.models.DBs.Genre;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DBs.SysUser;
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
        SysUser writer = (SysUser)execution.getVariable("loggedInWriter");
        Genre genre = genreService.getGenre(Integer.parseInt(newPublishedBookForm.get("genre").toString()));

        PublishedBook newPublishedBook = bookService.storeInitialBookDetails(newPublishedBookForm, writer, genre);

        execution.setVariable("bookTitle", newPublishedBook.getTitle());
        execution.setVariable("genreName", newPublishedBook.getGenre().getName());
    }
}
