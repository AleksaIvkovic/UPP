package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IPlagiarism;
import com.example.workflow.models.DBs.PublishedBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlagiarismServiceMock implements IPlagiarism {
    @Override
    public ArrayList<String> checkPlagiarism(PublishedBook publishedBook) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Possible plagiarism 1");
        list.add("Possible plagiarism 2");
        return list;
    }
}
