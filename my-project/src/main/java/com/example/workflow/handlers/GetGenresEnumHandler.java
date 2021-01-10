package com.example.workflow.handlers;

import com.example.workflow.models.DBs.Genre;
import com.example.workflow.services.systemServices.GenreService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetGenresEnumHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        ArrayList<Genre> genres = (ArrayList<Genre>)delegateTask.getVariable("allGenres");

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("genre")) {
                EnumFormType enumFormType = (EnumFormType) f.getType();

                for (Genre genre : genres) {
                    enumFormType.getValues().put(Long.toString(genre.getId()), genre.getName());
                }
            }
        }
    }
}
