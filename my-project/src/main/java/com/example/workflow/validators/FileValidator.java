package com.example.workflow.validators;

import com.example.workflow.models.FileDTO;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.ArrayList;
import java.util.HashMap;

public class FileValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        return ((ArrayList<String>)submittedValue).toArray().length >= 2;
    }
}
