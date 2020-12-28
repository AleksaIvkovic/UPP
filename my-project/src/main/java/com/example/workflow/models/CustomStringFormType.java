package com.example.workflow.models;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.StringFormType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.List;

public class CustomStringFormType extends StringFormType {
    private String typeName;
    private  String value;

    public CustomStringFormType() {
        super();
    }

    public CustomStringFormType(String  typeName) {
        super();
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return "string_".concat(typeName);
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        Object value = propertyValue.getValue();
        return value == null ? Variables.stringValue((String) null, propertyValue.isTransient()) : Variables.stringValue(value.toString(), propertyValue.isTransient());
    }

    public Object convertFormValueToModelValue(Object propertyValue) {
        return propertyValue;
    }

    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue != null) {
            if (!(modelValue instanceof List)) {
                throw new ProcessEngineException("Model value should be a List customstringformtype");
            }
        }

        return modelValue.toString();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

