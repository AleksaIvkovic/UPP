package com.example.workflow.handlers;

import com.example.workflow.models.Genre;
import com.example.workflow.models.MultipleEnumFormType;
import com.example.workflow.services.GetGenresService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GetGenresHandler implements TaskListener {

    @Autowired
    GetGenresService getGenresService;

    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<Genre> genres = getGenresService.getGenres();

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("genres")) {
                MultipleEnumFormType multipleEnumFormType = (MultipleEnumFormType) f.getType();

                for (Genre genre : genres) {
                    multipleEnumFormType.getValues().put(Long.toString(genre.getId()), genre.getName());
                }
            }
        }
    }
}
