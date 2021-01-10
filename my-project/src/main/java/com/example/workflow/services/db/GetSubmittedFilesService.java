package com.example.workflow.services.db;

import com.example.workflow.models.DBs.SubmittedFile;
import com.example.workflow.repositories.FileRepository;
import com.example.workflow.services.systemServices.SubmittedFileService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetSubmittedFilesService implements JavaDelegate {
    @Autowired
    private SubmittedFileService submittedFileService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<SubmittedFile> files = submittedFileService.getAllFilesByProcessId(delegateExecution.getProcessInstanceId());
        delegateExecution.setVariable("submittedFiles", files);
    }
}
