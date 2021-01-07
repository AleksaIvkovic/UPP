package com.example.workflow.services.db;

import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.SystemUserService;
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
            sysUser.setConfirmed(true);
            systemUserService.storeSystemUser(sysUser);
        }
    }
}
