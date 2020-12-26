package com.example.workflow.services;

import com.example.workflow.intefaces.IPlagiarism;
import com.example.workflow.models.PublishedBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckPlagiarismServiceMock implements IPlagiarism {
    @Override
    public ArrayList<String> checkPlagiarism(PublishedBook publishedBook) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Possible plagiarism 1");
        list.add("Possible plagiarism 2");
        return list;
    }
}
