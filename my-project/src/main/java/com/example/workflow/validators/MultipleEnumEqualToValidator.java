package com.example.workflow.validators;

import com.example.workflow.helper.TempHelper;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;

public class MultipleEnumEqualToValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        return TempHelper.getFound((HashMap<String, Boolean>) o) == Integer.parseInt(formFieldValidatorContext.getVariableScope().getVariable("numOfSubsNeeded").toString());
    }
}