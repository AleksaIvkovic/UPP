package com.example.workflow.services.db;

import com.example.workflow.models.DBs.Authority;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.AuthorityService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoseBetaStatusService implements JavaDelegate {
    @Autowired
    private SystemUserService sysUserService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<SysUser> loseBetaStatusReaders = (ArrayList<SysUser>)execution.getVariable("loseBetaStatusReaders");
        List<Authority> authorities = new ArrayList<>();
        Authority authority = authorityService.findByName("READER");
        authorities.add(authority);

        for(SysUser user: loseBetaStatusReaders){
            sysUserService.loseBetaStatus(user, authorities);
            identityService.deleteMembership(user.getUsername(),"betaReaders");
            identityService.createMembership(user.getUsername(), "readers");
        }
    }
}
