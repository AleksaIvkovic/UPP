package com.example.workflow.intefaces;

import com.example.workflow.models.DBs.Genre;

import java.util.List;

public interface IGenre {
    List<Genre> getGenres();
    Genre getGenre(long id);
}
