package com.example.workflow.handlers;

import com.example.workflow.models.Genre;
import com.example.workflow.services.GenreService;
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
import java.util.List;

@Service
public class GetEditorsHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        ArrayList<User> editors = (ArrayList<User>)delegateTask.getExecution().getVariable("remainingEditors");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("editors") || f.getId().equals("substitutes")) {
                EnumFormType enumFormType = (EnumFormType) f.getType();

                for (User editor : editors) {
                    enumFormType.getValues().put(editor.getId(), editor.getFirstName().concat(" ").concat(editor.getLastName()) );
                }
            }
        }


    }
}
