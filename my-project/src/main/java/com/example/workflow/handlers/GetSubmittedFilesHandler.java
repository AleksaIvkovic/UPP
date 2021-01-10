package com.example.workflow.handlers;

import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DBs.SubmittedFile;
import com.example.workflow.models.customs.NonEditableMultipleEnumFormType;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetSubmittedFilesHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        ArrayList<SubmittedFile> files = (ArrayList<SubmittedFile>)delegateTask.getVariable("submittedFiles");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("works")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();

                for (SubmittedFile file : files) {
                    nonEditableMultipleEnumFormType.getValues().put(Long.toString(file.getId()), file.getName());
                }
            }
        }
    }
}
