package com.example.workflow.intefaces;

import com.example.workflow.models.Reader;

public interface IReader {
    Reader getReaderById(Long id);
    Reader getReaderByUsername(String username);
    void storeReader(Reader newReader);
    boolean checkUniqueUsername(String username);
    boolean checkUniqueEmail(String email);
    //Update reader
}
