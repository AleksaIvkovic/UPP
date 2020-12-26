package com.example.workflow.handlers;

import com.example.workflow.models.CustomStringFormType;
import com.example.workflow.models.PublishedBook;
import com.example.workflow.services.BookService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetBookSynopsisHandler implements TaskListener {
    @Autowired
    private BookService bookService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        PublishedBook publishedBook = bookService.GetBookByTitle(delegateTask.getExecution().getVariable("bookTitle").toString());

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("titleReadOnly")) {
                CustomStringFormType customStringFormType = (CustomStringFormType) f.getType();
                customStringFormType.setValue(publishedBook.getTitle());
            }
            if (f.getId().equals("genreReadOnly")) {
                CustomStringFormType customStringFormType = (CustomStringFormType) f.getType();
                customStringFormType.setValue(publishedBook.getGenre().getName());
            }
            if (f.getId().equals("synopsisReadOnly")) {
                CustomStringFormType customStringFormType = (CustomStringFormType) f.getType();
                customStringFormType.setValue(publishedBook.getSynopsis());
            }
        }
    }
}
