package com.example.workflow.services;

import com.example.workflow.models.FileDTO;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class FileService {

    @Autowired
    TaskService taskService;

    public void savePDF(FileDTO fileDto, String taskId){
        //log.info("Save PDF");
        MultipartFile multipartFile = fileDto.getFile();
        Path filepath = Paths.get(".\\src\\main\\resources\\PDFFiles", multipartFile.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
