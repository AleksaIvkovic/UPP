package com.example.workflow.intefaces;

import com.example.workflow.models.DBs.SubmittedFile;

import java.util.ArrayList;

public interface ISubmittedFile {
    ArrayList<SubmittedFile> getAllFilesByProcessId(String id);
}
