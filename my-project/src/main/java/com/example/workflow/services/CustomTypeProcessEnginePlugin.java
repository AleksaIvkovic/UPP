package com.example.workflow.services;

import com.example.workflow.models.*;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.form.type.StringFormType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTypeProcessEnginePlugin extends AbstractProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (processEngineConfiguration.getCustomFormTypes() == null) {
            processEngineConfiguration.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
        }

        List<AbstractFormFieldType> formTypes = processEngineConfiguration.getCustomFormTypes();
        formTypes.add(new FileFormType("2_10"));
        formTypes.add(new MultipleEnumFormType("genres"));
        formTypes.add(new CustomStringFormType("email"));
        formTypes.add(new CustomStringFormType("password"));
        formTypes.add(new CustomStringFormType("textArea"));
    }
}
