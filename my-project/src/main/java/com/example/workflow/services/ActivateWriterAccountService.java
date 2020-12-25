package com.example.workflow.services;

import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivateWriterAccountService implements JavaDelegate {
    @Autowired
    private ISystemUser systemUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SysUser sysUser = systemUserService.getSystemUserByEmail(execution.getVariable("email").toString());

        if(sysUser != null){
            sysUser.setActive(true);
            systemUserService.storeSystemUser(sysUser);
        }
    }
}
