package com.example.workflow.validators;

import com.example.workflow.handlers.ServiceUtils;
import com.example.workflow.services.ReaderService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class UniqueUsernameValidator implements FormFieldValidator {

    @Autowired
    ReaderService readerService;

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        //return  true;
        readerService = ServiceUtils.getReaderService();
        return  readerService.checkUniqueUsername((String)o);
    }
}
