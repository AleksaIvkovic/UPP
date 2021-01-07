package com.example.workflow.services.db;

import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddPenaltyPointService implements JavaDelegate {

    @Autowired
    private SystemUserService sysUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> selectedBetaUsernames = ( ArrayList<String>)execution.getVariable("selectedBetaReadersUsernames");
        ArrayList<User> haveCommented = ( ArrayList<User>)execution.getVariable("haveCommented");
        ArrayList<String> haveCommentedUsernames = new ArrayList<>();
        ArrayList<String> haveNotCommented = new ArrayList<>();

        for(User user : haveCommented){
            haveCommentedUsernames.add(user.getId());
        }

        for (String username: selectedBetaUsernames) {
            if(haveCommentedUsernames.contains(username))
                continue;
            SysUser sysUser = sysUserService.getSystemUserByUsername(username);
            sysUser.setPoints(sysUser.getPoints()+1);
            sysUserService.storeSystemUser(sysUser);
            haveNotCommented.add(username);
        }

        execution.setVariable("haveNotCommented",haveNotCommented);

    }
}
