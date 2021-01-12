package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.ICamunda;
import com.example.workflow.models.DTOs.FormSubmissionDTO;
import com.example.workflow.models.DTOs.TaskDTO;
import com.example.workflow.models.ValidationError;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CamundaService implements ICamunda {
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;

    @Override
    public HashMap<String, Object> mapListToDTO(List<FormSubmissionDTO> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    @Override
    public ResponseEntity<?> trySubmitForm(String taskId, HashMap<String, Object> map) {
        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            try {
                return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
            } catch (Exception ex) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getNextTask(String taskName, String processInstanceId) {
        if (taskService.createTaskQuery().processInstanceId(processInstanceId).list().size() != 0) {
            Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
            if (nextTask.getName().equals(taskName)) {
                TaskDTO taskDTO = new TaskDTO(nextTask.getId(), nextTask.getName());
                return new ResponseEntity<>(taskDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}