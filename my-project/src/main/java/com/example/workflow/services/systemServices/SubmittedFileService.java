package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.ISubmittedFile;
import com.example.workflow.models.DBs.SubmittedFile;
import com.example.workflow.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubmittedFileService implements ISubmittedFile {
    @Autowired
    private FileService fileService;

    @Override
    public ArrayList<SubmittedFile> getAllFilesByProcessId(String id) {
        return fileService.getAllByProcessId(id);
    }
}
