package com.example.workflow.services;

import com.example.workflow.intefaces.IBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckPlagiarismService implements JavaDelegate
{
    @Autowired
    private  CheckPlagiarismServiceMock checkPlagiarismServiceMock;

    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        ArrayList<String> possiblePlagiarisms = checkPlagiarismServiceMock.checkPlagiarism(bookService.GetBookByTitle(execution.getVariable("bookTitle").toString()));

        execution.setVariable("possiblePlagiarisms",possiblePlagiarisms);

    }
}
