package com.example.workflow.services;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveEmailConfirmationService implements JavaDelegate {
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String token = execution.getVariable("TokenValidationToken").toString();
        SysUser sysUser = systemUserService.findSystemUserByToken(token);

        if(sysUser != null){
            sysUser.setActive(true);
            systemUserService.storeSystemUser(sysUser);
        }
    }
}
