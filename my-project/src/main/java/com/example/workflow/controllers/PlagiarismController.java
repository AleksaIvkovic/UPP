package com.example.workflow.controllers;

import com.example.workflow.models.FormSubmissionDTO;
import com.example.workflow.models.Genre;
import com.example.workflow.models.SysUser;
import com.example.workflow.services.SystemUserService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/plagiarism")
public class PlagiarismController {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    IdentityService identityService;

    @PostMapping(path="/submit-appeal/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postAppealForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-chosen-editors/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postHeadEditorChoiceForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        List<User> chosenEditors = new ArrayList<>();
        List<User> remainingEditors = (ArrayList<User>)runtimeService.getVariable(processInstanceId,"remainingEditorsUsers");
        HashMap<String, Boolean> editorsHM = (HashMap<String, Boolean>)(map.get("editors"));

        for (Map.Entry mapElement: editorsHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                User temp = identityService.createUserQuery().userId(mapElement.getKey().toString()).singleResult();
                chosenEditors.add(temp);
                for(User remaining: remainingEditors){
                    if(remaining.getId().toString().equals(temp.getId().toString())){
                        remainingEditors.remove(remaining);
                        break;
                    }
                }
            }
        }

        ArrayList<String> editorUsernames = new ArrayList<>();
        ArrayList<String> remainingEditorUsernames = new ArrayList<>();

        for(User editor: chosenEditors){
            editorUsernames.add(editor.getId());
        }

        for(User editor: remainingEditors){
            remainingEditorUsernames.add(editor.getId());
        }

        runtimeService.setVariable(processInstanceId,"editorsUsers", chosenEditors);
        runtimeService.setVariable(processInstanceId,"remainingEditorsUsers", remainingEditors);
        runtimeService.setVariable(processInstanceId,"editorsUsernames", editorUsernames);
        runtimeService.setVariable(processInstanceId,"remainingEditorsUsernames", remainingEditorUsernames);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-chosen-substitutes/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postSubstituteChoiceForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<User> chosenSubstitutes = new ArrayList<>();

        HashMap<String, Boolean> editorsHM = (HashMap<String, Boolean>)(map.get("editors"));

        for (Map.Entry mapElement: editorsHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                User temp = identityService.createUserQuery().userId(mapElement.getKey().toString()).singleResult();
                chosenSubstitutes.add(temp);
            }
        }
        runtimeService.setVariable(processInstanceId,"chosenSubstitutes", chosenSubstitutes);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-editor-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postEditorReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> notes = (ArrayList<String>)runtimeService.getVariable(processInstanceId,"editorsNotes");
        notes.add(map.get("note").toString());
        runtimeService.setVariable(processInstanceId,"editorsNotes", notes);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        ArrayList<User> haveVoted = (ArrayList<User>)runtimeService.getVariable(processInstanceId,"haveVoted");
        haveVoted.add(identityService.createUserQuery().userId(sysUser.getUsername()).singleResult());
        runtimeService.setVariable(processInstanceId,"haveVoted", haveVoted);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-committee-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCommitteeReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> votes = (ArrayList<String>)runtimeService.getVariable(processInstanceId,"committeeVotes");
        votes.add(map.get("vote").toString());
        runtimeService.setVariable(processInstanceId,"committeeVotes",votes);

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
