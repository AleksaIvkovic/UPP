package com.example.workflow.models;

import org.camunda.bpm.engine.impl.form.type.StringFormType;

public class EmailStringFormType extends StringFormType {
    private String typeName;

    public EmailStringFormType(String  typeName) {
        super();
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return "string_".concat(typeName);
    }
}
