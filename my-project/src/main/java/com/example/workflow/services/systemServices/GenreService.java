package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IGenre;
import com.example.workflow.models.DBs.Genre;
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
    public ArrayList<Genre> getGenres(){
        return (ArrayList<Genre>) this.genreRepository.findAll();
    }

    @Override
    public Genre getGenre(long id) {
        Optional<Genre> genre = this.genreRepository.findById(id);
        if(genre.get() == null){
            throw new NotFoundException();
        }
        return genre.get();
    }
}
