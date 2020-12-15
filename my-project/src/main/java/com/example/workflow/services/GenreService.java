package com.example.workflow.services;

import com.example.workflow.intefaces.IGenre;
import com.example.workflow.models.Genre;
import com.example.workflow.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService implements IGenre {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getGenres(){
        return this.genreRepository.findAll();

        /*
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre(0,"Romance"));
        genres.add(new Genre(1,"Fiction"));
        genres.add(new Genre(2,"Horror"));
        genres.add(new Genre(3,"History"));

        return genres;
        */
    }

    @Override
    public Genre getGenre(long id) {
        Genre genre = this.genreRepository.getOne(id);
        if(genre == null){
            throw new NotFoundException();
        }
        return  genre;
    }
}
