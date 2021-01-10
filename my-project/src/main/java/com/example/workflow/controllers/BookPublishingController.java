package com.example.workflow.controllers;

import com.example.workflow.helper.TempHelper;
import com.example.workflow.intefaces.ICamunda;
import com.example.workflow.models.DTOs.FormSubmissionDTO;
import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/book-publishing")
public class BookPublishingController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ICamunda camundaService;
    @Autowired
    private IdentityService identityService;

    @PostMapping(path="/submit-commentManuscript-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCommentManuscriptForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            return new ResponseEntity<>("Task is no longer valid",HttpStatus.BAD_REQUEST);
        }
        String processInstanceId = task.getProcessInstanceId();

        TempHelper.editCommentsAndHaveCommentedLists(identityService, runtimeService, map, processInstanceId);

        return camundaService.trySubmitForm(taskId, map);
    }
}