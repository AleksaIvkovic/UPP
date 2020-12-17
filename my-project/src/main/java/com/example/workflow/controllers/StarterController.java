package com.example.workflow.controllers;

import camundajar.impl.scala.util.parsing.json.JSON;
import com.example.workflow.models.FormFieldsDTO;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/starter")
public class StarterController {
    @Autowired
    private RuntimeService runtimeService;

    //@Autowired
    //private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(path = "/reader-registration", produces = "application/json")
    public @ResponseBody
    HashMap<String, Object> startReaderProcess() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("register");
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("processId",pi.getId());
        return hm;
    }
}
