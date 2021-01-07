package com.example.workflow.handlers;

import com.example.workflow.models.*;
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
public class GetDownloadableFilesHandler implements TaskListener {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    BookService bookService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<SubmittedFile> files = fileRepository.getAllByProcessId(delegateTask.getProcessInstanceId());
        List<PublishedBook> books = new ArrayList<>();

        if(delegateTask.getVariable("myTitle") != null){
            books.add(bookService.GetBookByTitle(delegateTask.getVariable("myTitle").toString()));
            books.add(bookService.GetBookByTitle(delegateTask.getVariable("title").toString()));
        }

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("works")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();

                for (SubmittedFile file : files) {
                    nonEditableMultipleEnumFormType.getValues().put(Long.toString(file.getId()), file.getName());
                }
            } else if (f.getId().equals("books")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();

                for (PublishedBook book : books) {
                    nonEditableMultipleEnumFormType.getValues().put(book.getTitle(), book.getFileName());
                }
            }
            if (f.getId().equals("work")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();

                for (SubmittedFile file : files) {
                    nonEditableMultipleEnumFormType.getValues().put(Long.toString(file.getId()), file.getName());
                }
            }
            if (f.getId().equals("updatedManuscript")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();

                for (SubmittedFile file : files) {
                    nonEditableMultipleEnumFormType.getValues().put(Long.toString(file.getId()), file.getName());
                }
            }
        }
    }
}