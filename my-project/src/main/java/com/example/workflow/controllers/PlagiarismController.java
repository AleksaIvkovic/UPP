package com.example.workflow.controllers;

import com.example.workflow.intefaces.ICamunda;
import com.example.workflow.models.FormSubmissionDTO;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ICamunda camundaService;

    @PostMapping(path="/submit-chosen-editors/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postHeadEditorChoiceForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
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
                    if(remaining.getId().equals(temp.getId())){
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

        return camundaService.trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit-chosen-substitutes/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postSubstituteChoiceForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
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

        return camundaService.trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit-editor-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postEditorReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> notes = (ArrayList<String>)runtimeService.getVariable(processInstanceId,"editorsNotes");
        notes.add(map.get("note").toString());
        runtimeService.setVariable(processInstanceId,"editorsNotes", notes);

        ArrayList<User> haveVoted = (ArrayList<User>)runtimeService.getVariable(processInstanceId,"haveVoted");
        haveVoted.add(identityService.createUserQuery().userId(sysUser.getUsername()).singleResult());
        runtimeService.setVariable(processInstanceId,"haveVoted", haveVoted);

        return camundaService.trySubmitForm(taskId, map);
    }

    @PostMapping(path="/submit-committee-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postCommitteeReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = camundaService.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> votes = (ArrayList<String>)runtimeService.getVariable(processInstanceId,"committeeVotes");
        votes.add(map.get("vote").toString());
        runtimeService.setVariable(processInstanceId,"committeeVotes",votes);

        return camundaService.trySubmitForm(taskId, map);
    }
}
