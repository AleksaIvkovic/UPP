package com.example.workflow.validators;

import com.example.workflow.helpers.TempHelper;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.HashMap;

public class MultipleEnumGreaterThanValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        return TempHelper.getFound((HashMap<String, Boolean>) o) >= Integer.parseInt(formFieldValidatorContext.getFormFieldHandler().getId().split("_")[1]);
    }
}