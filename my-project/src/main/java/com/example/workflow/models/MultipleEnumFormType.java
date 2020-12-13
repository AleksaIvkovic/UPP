package com.example.workflow.models;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.List;

public class MultipleEnumFormType extends EnumFormType {

    private String typeName;

    public MultipleEnumFormType() {
        super(new HashMap<>());
    }

    public MultipleEnumFormType(String typeName) {
        super(new HashMap<>());
        this.typeName = typeName;
    }

    public String getName() {
        return "multipleEnum_".concat(typeName);
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        Object value = propertyValue.getValue();
        /*if (value != null && !String.class.isInstance(value)) {
            throw new ProcessEngineException("Value '" + value + "' is not of type List.");
        } else {*/
        //this.validateValue(value);
        return value == null ? Variables.stringValue((String) null, propertyValue.isTransient()) : Variables.stringValue(value.toString(), propertyValue.isTransient());
        //}
    }

    public Object convertFormValueToModelValue(Object propertyValue) {
        //this.validateValue(propertyValue);
        return propertyValue;
    }

    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue != null) {
            if (!(modelValue instanceof List)) {
                throw new ProcessEngineException("Model value should be a List");
            }

            //this.validateValue(modelValue);
        }

        return modelValue.toString();
    }
}
