package com.example.workflow.controllers;

import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.models.DTOs.FormFieldsDTO;
import com.example.workflow.models.DTOs.TaskDTO;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TasksController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD-COMMITTEE', 'WRITER','HEAD-EDITOR','EDITOR','BETA-READER','LECTOR')")
    public ResponseEntity<?> GetTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(sysUser.getUsername()).list();

        ArrayList<TaskDTO> taskDTOs = new ArrayList<>();

        tasks.forEach(task -> {
            taskDTOs.add(new TaskDTO(task.getId(), task.getName()));
        });

        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSingleTask/{taskId}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD-COMMITTEE','WRITER','HEAD-EDITOR','EDITOR','BETA-READER','LECTOR')")
    public @ResponseBody
    FormFieldsDTO GetTaskForm(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        TaskFormData tfd = formService.getTaskFormData(taskId);
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDTO(taskId, properties, processInstanceId);
    }
}
