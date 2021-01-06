package com.example.workflow.handlers;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetBetaReadersHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        ArrayList<User> betaReaders = (ArrayList<User>) delegateTask.getExecution().getVariable("chosenBetaReaders");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("betaReaders_1")) {
                EnumFormType enumFormType = (EnumFormType) f.getType();

                for (User betaReader : betaReaders) {
                    enumFormType.getValues().put(betaReader.getId(), betaReader.getFirstName().concat(" ").concat(betaReader.getLastName()));
                }
            }
        }
    }
}
