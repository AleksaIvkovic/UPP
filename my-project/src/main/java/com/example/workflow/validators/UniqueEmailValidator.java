package com.example.workflow.validators;

import com.example.workflow.handlers.ServiceUtils;
import com.example.workflow.services.SystemUserService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements FormFieldValidator {

    @Autowired
    SystemUserService systemUserService;

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        systemUserService = ServiceUtils.getSystemUserService();
        return systemUserService.checkUniqueEmail((String)o);
    }
}
