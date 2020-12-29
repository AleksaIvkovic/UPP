package com.example.workflow.controllers;

import com.example.workflow.models.*;
import com.example.workflow.services.systemServices.FileService;
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

    @Autowired
    FileService fileService;

    @GetMapping(path = "/form/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO getForm(@PathVariable String processId) {
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDTO(task.getId(), properties, processId);
    }

    @PostMapping(path="/submit-registration-form/{taskId}", consumes = "application/json")
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

    //@PreAuthorize("hasAnyAuthority('CLINICADMIN','PATIENT')") ako jedan onda hasAuthority
    @PostMapping(path="/submit-work/{taskId}", consumes = "application/json")
    public ResponseEntity<?> submitWork(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"worksToStore", map);

        /*
        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        String processInstanceId = task.getProcessInstanceId();
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)runtimeService.getVariable(processInstanceId,"newSysUser");

        String username = systemUserForm.get("username").toString();

        for (Map.Entry mapElement: map.entrySet()) {
            ArrayList<String> fileNames = (ArrayList<String>)mapElement.getValue();
            for (String name : fileNames){
                SubmittedFile newFile = new SubmittedFile(name,processInstanceId,username);
                fileService.storeSubmittedFile(newFile);
            }
        }
         */

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
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

    @PostMapping(path="/submit-payment-details/{taskId}", consumes = "application/json")
    public ResponseEntity<?> SubmitPaymentDetails(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"creditCard", map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-vote-new-writer/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postVoteNewWriterForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> committeeVotes = (ArrayList<String>)runtimeService.getVariable(processInstanceId, "committeeVotes");
        ArrayList<String> committeeComments = (ArrayList<String>)runtimeService.getVariable(processInstanceId, "committeeVotes");

        committeeVotes.add(map.get("vote").toString());
        committeeComments.add(map.get("comment").toString());

        runtimeService.setVariable(processInstanceId, "committeeVotes", committeeVotes);
        runtimeService.setVariable(processInstanceId, "committeeComments", committeeComments);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

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
