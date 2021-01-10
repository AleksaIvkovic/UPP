package com.example.workflow.services.db;

import com.example.workflow.models.DBs.Genre;
import com.example.workflow.services.systemServices.GenreService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetGenresService implements JavaDelegate {
    @Autowired
    private GenreService genreService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<Genre> genres = genreService.getGenres();
        delegateExecution.setVariable("allGenres", genres);
    }
}
