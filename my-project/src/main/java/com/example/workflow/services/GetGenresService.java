package com.example.workflow.services;

import com.example.workflow.intefaces.IGetGenres;
import com.example.workflow.models.Genre;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetGenresService implements IGetGenres {

    @Override
    public List<Genre> getGenres(){

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre(0,"Romance"));
        genres.add(new Genre(1,"Fiction"));
        genres.add(new Genre(2,"Horror"));
        genres.add(new Genre(3,"History"));

        return genres;
        /*String var = "Pera";
        var = var.toUpperCase();
        execution.setVariable("input", var);
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
        System.out.println(registration);
        User user = identityService.newUser("");
        for (FormSubmissionDto formField : registration) {
            if(formField.getFieldId().equals("username")) {
                user.setId(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("password")) {
                user.setPassword(formField.getFieldValue());
            }
        }
        identityService.saveUser(user);*/
    }
}
