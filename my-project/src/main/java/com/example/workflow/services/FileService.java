package com.example.workflow.services;

import com.example.workflow.intefaces.IFile;
import com.example.workflow.models.FileDTO;
import com.example.workflow.models.SubmittedFile;
import com.example.workflow.repositories.FileRepository;
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
import java.util.Properties;

@Service
public class FileService implements IFile {

    @Autowired
    TaskService taskService;

    @Autowired
    private FileRepository fileRepository;

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

    @Override
    public void storeSubmittedFile(SubmittedFile newSubmittedFile) {
        fileRepository.save(newSubmittedFile);
    }
}
