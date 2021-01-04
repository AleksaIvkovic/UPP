package com.example.workflow.controllers;


import com.example.workflow.models.FileDTO;
import com.example.workflow.services.systemServices.FileService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload/{taskId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPDF(@ModelAttribute FileDTO fileDto, @PathVariable String taskId) {
        fileService.savePDF(fileDto, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/download/{name}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> downloadPDF(@PathVariable String name) throws MalformedURLException {
        return fileService.download(name);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);
        String contentType = fileService.getContentType(request, resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
