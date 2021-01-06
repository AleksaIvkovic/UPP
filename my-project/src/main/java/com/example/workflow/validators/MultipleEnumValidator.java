package com.example.workflow.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.HashMap;
import java.util.Map;

public class MultipleEnumValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        int found = 0;
        for(Boolean value : ((HashMap<String,Boolean>)o).values())
        {
            found = value ? found + 1: found;
        }

        if(formFieldValidatorContext.getFormFieldHandler().getId().split("_")[1].toString().equals("x"))
        {
            int exact = Integer.parseInt(formFieldValidatorContext.getVariableScope().getVariable("numOfSubsNeeded").toString());
            return found == exact;
        }
        else{
            int min = Integer.parseInt(formFieldValidatorContext.getFormFieldHandler().getId().split("_")[1]);
            return found >= min;
        }
    }
}
