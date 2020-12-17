package com.example.workflow.services;

import com.example.workflow.intefaces.IGenre;
import com.example.workflow.models.Genre;
import com.example.workflow.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService implements IGenre {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getGenres(){
        return this.genreRepository.findAll();
    }

    @Override
    public Genre getGenre(long id) {
        Optional<Genre> genre = this.genreRepository.findById(id);
        if(genre.get() == null){
            throw new NotFoundException();
        }
        return  genre.get();
    }
}
