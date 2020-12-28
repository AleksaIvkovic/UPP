package com.example.workflow.handlers;

import com.example.workflow.models.CustomStringFormType;
import com.example.workflow.models.NonEditableMultipleEnumFormType;
import com.example.workflow.models.ReadOnlyFieldType;
import com.example.workflow.models.SubmittedFile;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetPossiblePlagiarismsHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        ArrayList<String> possiblePlagiarisms = (ArrayList<String>) delegateTask.getExecution().getVariable("possiblePlagiarisms");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("possiblePlagiarisms")) {
                CustomStringFormType customStringFormType = (CustomStringFormType) f.getType();

                customStringFormType.convertModelValueToFormValue(possiblePlagiarisms);
            }
        }
    }
}
