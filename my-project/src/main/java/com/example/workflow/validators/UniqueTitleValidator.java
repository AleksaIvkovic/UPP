package com.example.workflow.validators;

import com.example.workflow.utils.ServiceUtils;
import com.example.workflow.services.systemServices.BookService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueTitleValidator implements FormFieldValidator {
    @Autowired
    private BookService bookService;

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        bookService = ServiceUtils.getBookService();
        return bookService.checkUniqueTitle((String)submittedValue);
    }
}
