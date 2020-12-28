package com.example.workflow.models;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.StringFormType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.List;

public class ReadOnlyFieldType extends StringFormType {
    private String typeName;

    public ReadOnlyFieldType() {
    }

    public ReadOnlyFieldType(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return "readOnly_".concat(typeName);
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
               // throw new ProcessEngineException("Model value should be a List");
            }
        }

        return modelValue.toString();
    }

    public void setValue(String title) {

    }
}
