package com.example.workflow.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

public class UniqueEmailValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        return true;
    }
}
