package com.example.workflow.services;

import com.example.workflow.intefaces.IReader;
import com.example.workflow.models.Reader;
import com.example.workflow.models.VerificationToken;
import com.example.workflow.repositories.ReaderRepository;
import com.example.workflow.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class ReaderService implements IReader {
    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

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
        Reader reader = readerRepository.getReaderByUsername(username);
        if (reader == null) {
            throw new NotFoundException();
        }
        return reader;
    }

    @Override
    public void storeReader(Reader newReader) {
        readerRepository.save(newReader);
    }

    @Override
    public boolean checkUniqueUsername(String username) {
        return readerRepository.getReaderByUsername(username) == null;
    }

    @Override
    public boolean checkUniqueEmail(String email) {
        return readerRepository.getReaderByEmail(email) == null;
    }

    @Override
    public void createVerificationToken(Reader reader, String token) {
        VerificationToken myToken = new VerificationToken(token, reader);
        verificationTokenRepository.save(myToken);
    }
}
