package com.example.workflow.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

public class EmailValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext formFieldValidatorContext) {
        return submittedValue.toString().matches("^(.+)@(.+)$");
    }
}
