package com.example.workflow.validators;

import com.example.workflow.handlers.ServiceUtils;
import com.example.workflow.models.PublishedBook;
import com.example.workflow.services.BookService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class BookExistsValidator implements FormFieldValidator {
    @Autowired
    BookService bookService;

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        bookService = ServiceUtils.getBookService();
        return bookService.GetBookByTitle((String)o) != null;
    }
}
