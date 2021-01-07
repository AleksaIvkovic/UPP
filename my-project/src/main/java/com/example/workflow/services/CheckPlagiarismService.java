package com.example.workflow.services;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.services.systemServices.PlagiarismServiceMock;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckPlagiarismService implements JavaDelegate
{
    @Autowired
    private PlagiarismServiceMock plagiarismServiceMock;

    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        ArrayList<String> possiblePlagiarisms = plagiarismServiceMock.checkPlagiarism(bookService.GetBookByTitle(execution.getVariable("bookTitle").toString()));
        execution.setVariable("possiblePlagiarisms",possiblePlagiarisms);
    }
}
