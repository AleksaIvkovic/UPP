package com.example.workflow.models;

import org.camunda.bpm.engine.impl.form.type.StringFormType;

public class CustomStringFormType extends StringFormType {
    private String typeName;

    public CustomStringFormType(String  typeName) {
        super();
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return "string_".concat(typeName);
    }
}

