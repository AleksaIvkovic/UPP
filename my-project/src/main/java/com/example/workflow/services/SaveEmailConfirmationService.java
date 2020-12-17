package com.example.workflow.services;

import com.example.workflow.models.Reader;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveEmailConfirmationService implements JavaDelegate {
    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String token = execution.getVariable("TokenValidationToken").toString();
        Reader reader = readerService.findReaderByToken(token);

        if(reader != null){
            reader.setConfirmed(true);
            readerService.storeReader(reader);
        }
    }
}
