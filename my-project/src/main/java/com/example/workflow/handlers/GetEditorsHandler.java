package com.example.workflow.handlers;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetEditorsHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        ArrayList<User> editors = (ArrayList<User>)delegateTask.getExecution().getVariable("remainingEditorsUsers");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().contains("editors")) {
                EnumFormType enumFormType = (EnumFormType) f.getType();

                enumFormType.getValues().clear();
                for (User editor : editors) {
                    enumFormType.getValues().put(editor.getId(), editor.getFirstName().concat(" ").concat(editor.getLastName()) );
                }
            }
        }


    }
}
