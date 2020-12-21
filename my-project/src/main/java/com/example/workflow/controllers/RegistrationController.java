package com.example.workflow.controllers;

import com.example.workflow.models.FormSubmissionDTO;
import com.example.workflow.models.FormFieldsDTO;
import com.example.workflow.models.TokenConfirmation;
import com.example.workflow.models.ValidationError;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/api/register")
public class RegistrationController {
    //@Autowired
    //IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    //@Autowired
    //private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;


    @GetMapping(path = "/registration-form/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO getRegistrationForm(@PathVariable String processId) {
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDTO(task.getId(), properties, processId);
    }

    @PostMapping(path="/submit-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postReaderForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "newSysUser", map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        Task nextTask;
        TaskFormData tfd = null;
        if(taskService.createTaskQuery().processInstanceId(processInstanceId).list().size() != 0) {
            nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
            tfd = formService.getTaskFormData(nextTask.getId());

            List<FormField> properties = tfd.getFormFields();
            for(FormField fp : properties) {
                System.out.println(fp.getId() + fp.getType());
            }

            return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), properties, processInstanceId), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path="/submit-beta-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postBetaForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "betaGenresForm", map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-work/{taskId}", consumes = "application/json")
    public ResponseEntity<?> submitWork(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        //HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        HashMap<String, Object> map = new HashMap<>();
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/confirm-email/{processId}", consumes = "application/json")
    public ResponseEntity<?> postBetaForm(@RequestBody TokenConfirmation object, @PathVariable String processId) {
        runtimeService.setVariable(processId, "TokenValidationToken", object.getToken());

        MessageCorrelationResult results = runtimeService.createMessageCorrelation("ReceiveSystemUserVerification")
                .processInstanceId(processId).correlateWithResult();

        return new ResponseEntity<>(HttpStatus.OK);
    }



    private HashMap<String, Object> mapListToDTO(List<FormSubmissionDTO> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
