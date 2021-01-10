package com.example.workflow.controllers;

import com.example.workflow.intefaces.ICamunda;
import com.example.workflow.models.DTOs.FormSubmissionDTO;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/camunda")
public class CamundaController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ICamunda camundaService;

    @PostMapping(path="/submit/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCamundaForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
        return camundaService.trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit/{variableName}/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCamundaFormWithVariable(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId, @PathVariable String variableName) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            return new ResponseEntity<>("Task is no longer valid", HttpStatus.BAD_REQUEST);
        }
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, variableName, map);

        return camundaService.trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit-return-task/{taskId}/{taskName}", consumes = "application/json")
    public ResponseEntity<?> postCamundaFormWithReturnTask(@RequestBody List<FormSubmissionDTO> dto,
                                                           @PathVariable String taskId,
                                                           @PathVariable String taskName) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            return new ResponseEntity<>("Task is no longer valid",HttpStatus.BAD_REQUEST);
        }
        String processInstanceId = task.getProcessInstanceId();

        camundaService.trySubmitForm(taskId, map);

        return camundaService.getNextTask(taskName, processInstanceId);
    }
}
