package com.example.workflow.services.db;

import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.DBs.SysUser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
