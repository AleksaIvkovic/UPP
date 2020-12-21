package com.example.workflow.models;

import org.camunda.bpm.engine.impl.form.type.StringFormType;

public class FileFormType extends StringFormType {
    private String typeName;

    public FileFormType(String  typeName) {
        super();
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return "files_".concat(typeName);
    }
}

