package com.example.workflow.services;

import com.example.workflow.models.SubmittedFile;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class StoreWorkService implements JavaDelegate {

    @Autowired
    FileService fileService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstanceId();

        HashMap<String, Object> map = (HashMap<String, Object>)delegateExecution.getVariable("worksToStore");

        String username = delegateExecution.getVariable("username").toString();

        for (Map.Entry mapElement: map.entrySet()) {
            ArrayList<String> fileNames = (ArrayList<String>)mapElement.getValue();
            for (String name : fileNames){
                SubmittedFile newFile = new SubmittedFile(name,processInstanceId,username);
                fileService.storeSubmittedFile(newFile);
            }
        }
    }
}
