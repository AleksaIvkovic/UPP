package com.example.workflow.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.ArrayList;

public class FileValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        int min = Integer.parseInt(validatorContext.getFormFieldHandler().getId().split("_")[1]);
        int max = Integer.parseInt(validatorContext.getFormFieldHandler().getId().split("_")[2]);
        return ((ArrayList<String>)submittedValue).size() >= min && ((ArrayList<String>)submittedValue).size() <= max;
    }
}
