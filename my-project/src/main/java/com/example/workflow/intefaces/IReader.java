package com.example.workflow.intefaces;

import com.example.workflow.models.Reader;
import com.example.workflow.models.VerificationToken;

public interface IReader {
    Reader getReaderById(Long id);
    Reader getReaderByUsername(String username);
    void storeReader(Reader newReader);
    boolean checkUniqueUsername(String username);
    boolean checkUniqueEmail(String email);
    void createVerificationToken(Reader reader, String token);
    //Update reader
}
