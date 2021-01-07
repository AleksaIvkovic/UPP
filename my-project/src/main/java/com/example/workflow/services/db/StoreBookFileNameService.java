package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.DBs.PublishedBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class StoreBookFileNameService implements JavaDelegate {
    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PublishedBook publishedBook = bookService.GetBookByTitle(delegateExecution.getVariable("bookTitle").toString());
        HashMap<String, Object> map = (HashMap<String, Object>)delegateExecution.getVariable("worksToStore");

        for (Map.Entry mapElement: map.entrySet()) {
            ArrayList<String> fileNames = (ArrayList<String>)mapElement.getValue();
            for (String name : fileNames){
                publishedBook.setFileName(name);
            }
        }

        bookService.StoreBook(publishedBook);
    }
}
