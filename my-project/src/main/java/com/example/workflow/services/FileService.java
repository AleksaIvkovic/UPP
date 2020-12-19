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
        Path filepath = Paths.get("C:\\Users\\sarap\\Desktop\\Master", multipartFile.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        if (task == null){
            //log.info("Task doesn't exist");
            throw new NotFoundException("Task exception");
        }
        ScientificWork scientificWork = (ScientificWork) this.runtimeService.getVariable(task.getProcessInstanceId(), "scientific_work");
        scientificWork.setFilePath(filepath.toString());
        this.scientificWorkRepository.save(scientificWork);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pdf_fajl", filepath.toString());
        formService.submitTaskForm(taskId, map);
        log.info("Form submitted");*/
    }

}
