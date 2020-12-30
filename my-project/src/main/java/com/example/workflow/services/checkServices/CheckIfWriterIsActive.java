package com.example.workflow.services.checkServices;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CheckIfWriterIsActive implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();
        //SysUser loggedInWriter = (SysUser) execution.getVariable("loggedInWriter");
        execution.setVariable("isActive", sysUser.isActive());
    }
}
