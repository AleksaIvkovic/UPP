package com.example.workflow.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.HashMap;
import java.util.Map;

public class GenresValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        return ((HashMap<String,Boolean>)o).values().contains(true);
    }
}
