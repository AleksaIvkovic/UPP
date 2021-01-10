package com.example.workflow.controllers;

import com.example.workflow.helper.TempHelper;
import com.example.workflow.intefaces.ICamunda;
import com.example.workflow.models.*;
import com.example.workflow.models.DTOs.FormFieldsDTO;
import com.example.workflow.models.DTOs.FormSubmissionDTO;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private ICamunda camundaService;

    @GetMapping(path = "/form/{processId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDTO getForm(@PathVariable String processId) {
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDTO(task.getId(), properties, processId);
    }

    @PostMapping(path="/submit-registration-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postReaderForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "newSysUser", map);

        camundaService.trySubmitForm(taskId, map);

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

    @PostMapping(path="/confirm-email/{processId}", consumes = "application/json")
    public ResponseEntity<?> EmailVerification(@RequestBody TokenConfirmation object, @PathVariable String processId) {
        runtimeService.setVariable(processId, "TokenValidationToken", object.getToken());

        MessageCorrelationResult results = runtimeService.createMessageCorrelation("ReceiveSystemUserVerification")
                .processInstanceId(processId).correlateWithResult();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-vote-new-writer/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postVoteNewWriterForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        TempHelper.editListWithValueInMap(runtimeService, processInstanceId, map, "committeeVotes", "vote");
        TempHelper.editListWithValueInMap(runtimeService, processInstanceId, map, "committeeComments", "comment");

        return camundaService.trySubmitForm(taskId, map);
    }
}