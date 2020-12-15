package com.example.workflow.intefaces;

import com.example.workflow.models.Reader;

public interface IReader {
    Reader getReaderById(Long id);
    Reader getReaderByUsername(String username);
    void storeReader(Reader newReader);
    //Update reader
}
