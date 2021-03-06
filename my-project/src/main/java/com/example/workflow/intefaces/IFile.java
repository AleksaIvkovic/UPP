package com.example.workflow.intefaces;

import com.example.workflow.models.DTOs.FileDTO;
import com.example.workflow.models.DBs.SubmittedFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.ArrayList;

public interface IFile {
    void storeSubmittedFile(SubmittedFile newSubmittedFile);
    String getContentType(HttpServletRequest request, Resource resource);
    Resource loadFileAsResource(String fileName);
    ResponseEntity<?> download(String name) throws MalformedURLException;
    void savePDF(FileDTO fileDto, String taskId);
    ArrayList<SubmittedFile> getAllByProcessId(String processId);
}
