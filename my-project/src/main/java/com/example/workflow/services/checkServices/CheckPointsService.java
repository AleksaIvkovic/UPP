package com.example.workflow.services.checkServices;

import com.example.workflow.models.SysUser;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CheckPointsService implements JavaDelegate {

    @Autowired
    private SystemUserService sysUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> haveNotCommented =  (ArrayList<String>)execution.getVariable("haveNotCommented");
        ArrayList<SysUser> loseBetaStatusReaders = new ArrayList<>();

        for (String username: haveNotCommented){
            SysUser user = sysUserService.getSystemUserByUsername(username);
            if(user.getPoints() >= 5)
                loseBetaStatusReaders.add(user);
        }

        if(loseBetaStatusReaders.isEmpty())
            execution.setVariable("loseBetaStatus", false);
        else {
            execution.setVariable("loseBetaStatus", true);
            execution.setVariable("loseBetaStatusReaders",loseBetaStatusReaders);
        }
    }
}
