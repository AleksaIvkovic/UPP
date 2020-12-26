package com.example.workflow.intefaces;

import com.example.workflow.models.PublishedBook;

import java.util.ArrayList;

public interface IPlagiarism {
    ArrayList<String> checkPlagiarism(PublishedBook publishedBook);
}
