package com.example.workflow.intefaces;

import com.example.workflow.models.FileDTO;
import com.example.workflow.models.SubmittedFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

public interface IFile {
    void  storeSubmittedFile(SubmittedFile newSubmittedFile);
    String getContentType(HttpServletRequest request, Resource resource);
    Resource loadFileAsResource(String fileName);
    ResponseEntity<?> download(String name) throws MalformedURLException;
    void savePDF(FileDTO fileDto, String taskId);
}
