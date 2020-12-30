package com.example.workflow.services.db;

import com.example.workflow.intefaces.IAuthority;
import com.example.workflow.models.Authority;
import com.example.workflow.models.SysUser;
import com.example.workflow.services.systemServices.AuthorityService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
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
        ArrayList<SysUser> loseBetaStatusReaders =   (ArrayList<SysUser>)execution.getVariable("loseBetaStatusReaders");

        for(SysUser user: loseBetaStatusReaders){
            user.getBetaGenres().clear();
            user.getAuthorities().clear();
            user.setBeta(false);
            sysUserService.storeSystemUser(user);

            Authority authoritie = authorityService.findByname("READER");
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authoritie);
            user.setAuthorities(authorities);
            sysUserService.storeSystemUser(user);

            identityService.deleteMembership(user.getUsername(),"betaReaders");
            identityService.createMembership(user.getUsername(), "readers");

        }
    }
}
