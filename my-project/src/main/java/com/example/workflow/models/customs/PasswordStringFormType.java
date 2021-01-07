package com.example.workflow.models.customs;

import org.camunda.bpm.engine.impl.form.type.StringFormType;

public class PasswordStringFormType extends StringFormType {
    private String typeName;

    public PasswordStringFormType(String  typeName) {
        super();
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return "string_".concat(typeName);
    }
}
