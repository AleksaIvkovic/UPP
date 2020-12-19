package com.example.workflow.models;

import org.camunda.bpm.engine.impl.form.type.StringFormType;

public class FileFormType extends StringFormType {
    public String getName() { return "files"; }
}

