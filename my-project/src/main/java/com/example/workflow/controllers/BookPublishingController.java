package com.example.workflow.controllers;

import com.example.workflow.models.FormSubmissionDTO;
import com.example.workflow.models.SysUser;
import com.example.workflow.models.ValidationError;
import org.camunda.bpm.engine.FormService;
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
    private FormService formService;
    @Autowired
    private IdentityService identityService;

    @PostMapping(path="/submit/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCamundaForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        return trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit/{variableName}/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCamundaFormWithVariable(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId, @PathVariable String variableName) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, variableName, map);

        return trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit-betaSelection-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postBetaSelectionForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "selectedBetaReadersForm", map.get("betaReaders"));

        return trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit-commentManuscript-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCommentManuscriptForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();
        User user = identityService.createUserQuery().userId(sysUser.getUsername()).singleResult();

        ArrayList<String> comments = (ArrayList<String>) runtimeService.getVariable(processInstanceId,"comments");
        ArrayList<User> haveCommented = (ArrayList<User>) runtimeService.getVariable(processInstanceId,"haveCommented");

        comments.add(map.get("comment").toString());
        haveCommented.add(user);

        runtimeService.setVariable(processInstanceId,"comments",comments);
        runtimeService.setVariable(processInstanceId,"haveCommented",haveCommented);

        return trySubmitForm(taskId, map);
    }

    private HashMap<String, Object> mapListToDTO(List<FormSubmissionDTO> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    private ResponseEntity<?> trySubmitForm(@PathVariable String taskId, HashMap<String, Object> map) {
        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}