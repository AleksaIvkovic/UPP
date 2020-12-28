package com.example.workflow.handlers;

import com.example.workflow.models.CustomStringFormType;
import com.example.workflow.models.PublishedBook;
import com.example.workflow.models.ReadOnlyFieldType;
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

        delegateTask.getExecution().setVariable("titleReadOnly",publishedBook.getTitle());
        delegateTask.getExecution().setVariable("genreReadOnly",publishedBook.getGenre().getName());
        delegateTask.getExecution().setVariable("synopsisReadOnly",publishedBook.getTitle());

        /*for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("titleReadOnly")) {
                CustomStringFormType customStringFormType1 = new CustomStringFormType();
                customStringFormType1 = (CustomStringFormType) f.getType();
                customStringFormType1.setValue(publishedBook.getTitle());
            }
            if (f.getId().equals("genreReadOnly")) {
                CustomStringFormType customStringFormType2 = (CustomStringFormType) f.getType();
                customStringFormType2.setValue(publishedBook.getGenre().getName());
            }
            if (f.getId().equals("synopsisReadOnly")) {
                CustomStringFormType customStringFormType3 = (CustomStringFormType) f.getType();
                customStringFormType3.setValue(publishedBook.getSynopsis());
            }
        }*/
    }
}
