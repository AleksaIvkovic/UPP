package com.example.workflow.controllers;

import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/api/starter")
public class StarterController {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(path = "/start/{processName}", produces = "application/json")
    public @ResponseBody
    HashMap<String, Object> startProcess(@PathVariable String processName) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName);
        if (processName.equals("registerReader")) {
            runtimeService.setVariable(pi.getId(), "systemUserRole", "READER");
        } else if (processName.equals("registerWriter")) {
            runtimeService.setVariable(pi.getId(), "systemUserRole", "WRITER");
        }
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("processId", pi.getId());
        return hm;
    }

    @GetMapping(path = "/start-writer-process/{processName}", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('WRITER')")
    public @ResponseBody
    ResponseEntity<?> startWriterProcess(@PathVariable String processName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        if(sysUser.isActive()){
            identityService.setAuthenticatedUserId(sysUser.getUsername());
            ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName);

            runtimeService.setVariable(pi.getId(), "loggedInWriter", sysUser);
            runtimeService.setVariable(pi.getId(), "loggedInEmail", sysUser.getEmail());
            runtimeService.setVariable(pi.getId(), "username", sysUser.getUsername());
            runtimeService.setVariable(pi.getId(), "firstname", sysUser.getFirstname());

            HashMap<String,Object> hm = new HashMap<>();
            hm.put("processId", pi.getId());
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
