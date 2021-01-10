package com.example.workflow.handlers;

import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DBs.SubmittedFile;
import com.example.workflow.models.customs.NonEditableMultipleEnumFormType;
import com.example.workflow.repositories.FileRepository;
import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetBooksToReviewHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<PublishedBook> books = (ArrayList<PublishedBook>)delegateTask.getVariable("booksToReview");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("books")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();

                for (PublishedBook book : books) {
                    nonEditableMultipleEnumFormType.getValues().put(book.getTitle(), book.getFileName());
                }
            }
        }
    }
}