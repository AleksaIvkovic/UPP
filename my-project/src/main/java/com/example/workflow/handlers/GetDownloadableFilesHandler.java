package com.example.workflow.handlers;

import com.example.workflow.models.*;
import com.example.workflow.repositories.FileRepository;
import com.example.workflow.services.BookService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.List;

@Service
public class GetDownloadableFilesHandler implements TaskListener {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    BookService bookService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<SubmittedFile> files = fileRepository.getAllByProcesId(delegateTask.getProcessInstanceId());

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("works")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                for (SubmittedFile file : files) {
                    nonEditableMultipleEnumFormType.getValues().put(Long.toString(file.getId()), file.getName());
                }
            } else if (f.getId().equals("original")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                PublishedBook book = bookService.GetBookByTitle(delegateTask.getVariable("myTitle").toString());
                nonEditableMultipleEnumFormType.getValues().put(book.getTitle(), book.getFileName());
            } else if (f.getId().equals("plagiarism")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                PublishedBook book = bookService.GetBookByTitle(delegateTask.getVariable("title").toString());
                nonEditableMultipleEnumFormType.getValues().put(book.getTitle(), book.getFileName());
            }
        }
    }
}