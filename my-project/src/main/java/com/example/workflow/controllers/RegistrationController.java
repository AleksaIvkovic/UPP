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

    @GetMapping(path = "/user-form", produces = "application/json")
    public @ResponseBody FormFieldsDTO get() {
        //provera da li korisnik sa id-jem pera postoji
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("register");

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDTO(task.getId(), properties, pi.getId());
    }

    @PostMapping(path="/submit-form/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);

//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);



        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
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
