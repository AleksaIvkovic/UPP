package com.example.workflow.services;

import com.example.workflow.intefaces.IReader;
import com.example.workflow.models.Reader;
import com.example.workflow.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class ReaderService implements IReader {
    @Autowired
    ReaderRepository readerRepository;

    @Override
    public Reader getReaderById(Long id) {
        Reader reader = readerRepository.getOne(id);
        if (reader == null) {
            throw new NotFoundException();
        }
        return reader;
    }

    @Override
    public Reader getReaderByUsername(String username) {
        Optional<Reader> reader = readerRepository.getReaderByUsername(username);
        if (reader.get() == null) {
            throw new NotFoundException();
        }
        return reader.get();
    }

    @Override
    public void storeReader(Reader newReader) {
        readerRepository.save(newReader);
    }
}
