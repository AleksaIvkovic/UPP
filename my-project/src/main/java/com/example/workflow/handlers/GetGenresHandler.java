package com.example.workflow.handlers;

import com.example.workflow.models.DBs.Genre;
import com.example.workflow.models.customs.MultipleEnumFormType;
import com.example.workflow.services.systemServices.GenreService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGenresHandler implements TaskListener {

    @Autowired
    GenreService genreService;

    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<Genre> genres = genreService.getGenres();

        for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("genres_1")) {
                MultipleEnumFormType multipleEnumFormType = (MultipleEnumFormType) f.getType();

                for (Genre genre : genres) {
                    multipleEnumFormType.getValues().put(Long.toString(genre.getId()), genre.getName());
                }
            }
        }
    }
}
