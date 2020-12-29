package com.example.workflow.plugins;

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
        formTypes.add(new FileFormType("1_10"));
        formTypes.add(new FileFormType("1_1"));
        formTypes.add(new MultipleEnumFormType("genres"));
        formTypes.add(new MultipleEnumFormType("betaReaders"));
        formTypes.add(new MultipleEnumFormType("editors"));
        formTypes.add(new CustomStringFormType("email"));
        formTypes.add(new CustomStringFormType("password"));
        formTypes.add(new CustomStringFormType("textArea"));
        formTypes.add(new CustomStringFormType("label"));
        formTypes.add(new CustomStringFormType("labels"));
        formTypes.add(new NonEditableMultipleEnumFormType("works"));
        formTypes.add(new ReadOnlyFieldType("string"));
    }
}
