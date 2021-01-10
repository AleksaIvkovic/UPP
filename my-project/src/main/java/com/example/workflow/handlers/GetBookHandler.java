package com.example.workflow.handlers;

import com.example.workflow.models.customs.NonEditableMultipleEnumFormType;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetBookHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("work")) {
                NonEditableMultipleEnumFormType nonEditableMultipleEnumFormType = (NonEditableMultipleEnumFormType) f.getType();

                nonEditableMultipleEnumFormType.getValues().clear();
                nonEditableMultipleEnumFormType.getValues().put(delegateTask.getVariable("bookTitle").toString(), delegateTask.getVariable("bookFileName").toString());
            }
        }
    }
}
