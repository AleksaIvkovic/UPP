package com.example.workflow.controllers;

import com.example.workflow.models.PublishedBook;
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
import java.util.Map;

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

    @PostMapping(path="/submit-synopsis-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postSynopsisForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "newPublishedBookForm", map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-explanation/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postExplanation(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "explanation", map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-synopsis-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postSynopsisReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        /*boolean denied;

        if (map.get("decision").toString().contains("Yes")) {
            denied = false;
        } else {
            denied = true;
        }

        runtimeService.setVariable(processInstanceId, "deniedSynopsis", denied);*/

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-manuscript/{taskId}", consumes = "application/json")
    public ResponseEntity<?> submitManuscript(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"worksToStore", map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-updated-manuscript/{taskId}", consumes = "application/json")
    public ResponseEntity<?> submitUpdatedManuscript(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"worksToStore", map);
        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-plagiarism-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postPlagiarismReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        /*Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        //boolean isPlagiarism;

        /*if (map.get("plagiarismDecision").toString().contains("Yes")) {
            isPlagiarism = true;
        } else {
            isPlagiarism = false;
        }

        runtimeService.setVariable(processInstanceId, "isPlagiarism", isPlagiarism);*/

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-manuscript-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postManuscriptReviewForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        /*Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        /*boolean manuscriptApproved;

        if (map.get("manuscriptDecision").toString().contains("Yes")) {
            manuscriptApproved = true;
        } else {
            manuscriptApproved = false;
        }

        runtimeService.setVariable(processInstanceId, "manuscriptApproved", manuscriptApproved);*/

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-sendToBeta-review/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postSendToBetaForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        //boolean needMoreWork = false;
        /*boolean sendToBeta;

        if (map.get("sendToBetaDecision").toString().contains("Yes")) {
            sendToBeta = true;
        } else {
            sendToBeta = false;
        }

        runtimeService.setVariable(processInstanceId, "sendToBeta", sendToBeta);*/
        runtimeService.setVariable(processInstanceId, "needMoreWork", false);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-more-changes-needed/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postMoreChangesNeededForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        boolean needMoreWork;

        if (map.get("decision").toString().contains("Yes")) {
            needMoreWork = true;
        } else {
            needMoreWork = false;
        }

        runtimeService.setVariable(processInstanceId, "needMoreWork", needMoreWork);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-betaSelection-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postBetaSelectionForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "selectedBetaReadersForm", map.get("betaReaders"));

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-lectorNotes-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postLectorNotesForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        /*if (map.get("lectorDecision").toString().contains("Yes")) {
            runtimeService.setVariable(processInstanceId,"lectorChangesNeeded", true);
        } else {
            runtimeService.setVariable(processInstanceId,"lectorChangesNeeded", false);
        }*/

        runtimeService.setVariable(processInstanceId, "lectorNote", map.get("lectorNote").toString());

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/submit-editor-approval-form/{taskId}", consumes = "application/json")
    public ResponseEntity<?> postEditorApprovalForm(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDTO(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        if (map.get("editorDecision").toString().contains("Yes")) {
            runtimeService.setVariable(processInstanceId,"editorChangesNeeded", true);
        } else {
            runtimeService.setVariable(processInstanceId,"editorChangesNeeded", false);
        }

        String ec = map.get("editorComment").toString();
        runtimeService.setVariable(processInstanceId, "editorComment", ec);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
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
