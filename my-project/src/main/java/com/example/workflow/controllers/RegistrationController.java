package com.example.workflow.controllers;

import com.example.workflow.models.FormSubmissionDTO;
import com.example.workflow.models.FormFieldsDTO;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/reader-form/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO getReaderForm(@PathVariable String processId) {
        //provera da li korisnik sa id-jem pera postoji
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        //ProcessInstance pi = runtimeService.startProcessInstanceByKey("register");

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
        runtimeService.setVariable(processInstanceId, "newReader", map);

        //try catch block
        formService.submitTaskForm(taskId, map);

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

        //try catch block
        formService.submitTaskForm(taskId, map);

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
