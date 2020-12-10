package com.example.workflow.models;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class FormFieldsDTO {
    String taksId;

    List<FormField>  formFields;

    String processInstanceId;

    public String getTaksId() {
        return taksId;
    }

    public void setTaksId(String taksId) {
        this.taksId = taksId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public FormFieldsDTO(String taksId, List<FormField> formFields, String processInstanceId) {
        this.taksId = taksId;
        this.formFields = formFields;
        this.processInstanceId = processInstanceId;
    }
}
