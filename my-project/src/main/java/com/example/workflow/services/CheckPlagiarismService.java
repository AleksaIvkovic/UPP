package com.example.workflow.services;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.services.systemServices.PlagiarismServiceMock;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckPlagiarismService implements JavaDelegate {
    @Autowired
    private PlagiarismServiceMock plagiarismServiceMock;
    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> possiblePlagiarisms = plagiarismServiceMock.checkPlagiarism(bookService.getBookByTitle(execution.getVariable("bookTitle").toString()));
        execution.setVariable("possiblePlagiarisms", possiblePlagiarisms);
    }
}
