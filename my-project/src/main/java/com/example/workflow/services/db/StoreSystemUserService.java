package com.example.workflow.services.db;

import com.example.workflow.intefaces.IGenre;
import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.DBs.Authority;
import com.example.workflow.models.DBs.Genre;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.AuthorityService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreSystemUserService implements JavaDelegate {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IGenre genreService;
    @Autowired
    private ISystemUser systemUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private TaskService taskService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");
        boolean isBeta;

        if(systemUserForm.get("isBeta") == null)
            isBeta = false;
        else
            isBeta = systemUserForm.get("isBeta")=="" ? false:(boolean)systemUserForm.get("isBeta");

        String group;
        String role = execution.getVariable("systemUserRole").toString();
        role = (isBeta && role.equals("READER")) ? "BETA-READER" : role;

        SysUser newSysUser = new SysUser(
                systemUserForm.get("firstname").toString(),
                systemUserForm.get("lastname").toString(),
                systemUserForm.get("city").toString(),
                systemUserForm.get("country").toString(),
                systemUserForm.get("username").toString(),
                passwordEncoder.encode(systemUserForm.get("password").toString()),
                systemUserForm.get("email").toString()
        );

        Authority authority = authorityService.findByName(role);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        newSysUser.setAuthorities(authorities);

        if (role.equals("READER")) {
            group = "readers";
        }
        else if(role.equals("BETA-READER")){
            group = "betaReaders";
            newSysUser.setPoints(0);
        }
        else{
            group = "writers";
        }

        List<Genre> genres = new ArrayList<>();
        HashMap<String, Boolean> genresHM = (HashMap<String, Boolean>)(systemUserForm.get("genres_1"));
        for (Map.Entry mapElement: genresHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                genres.add(genreService.getGenre(Long.parseLong(mapElement.getKey().toString())));
            }
        }

        newSysUser.setGenres(genres);

        if (isBeta) {
            newSysUser.setBeta(true);
            HashMap<String, Object> betaGenresForm;
            List<Genre> betaGenres = new ArrayList<>();

            betaGenresForm = (HashMap<String, Object>)execution.getVariable("betaGenresForm");

            HashMap<String, Boolean> betaGenresHM = (HashMap<String, Boolean>)(betaGenresForm.get("betaGenres_1"));
            for (Map.Entry mapElement: betaGenresHM.entrySet()) {
                if ((boolean)mapElement.getValue()) {
                    betaGenres.add(genreService.getGenre(Long.parseLong(mapElement.getKey().toString())));
                }
            }
            newSysUser.setBetaGenres(betaGenres);
        }
        else{
            newSysUser.setActive(false);
        }

        try {
            systemUserService.storeSystemUser(newSysUser);
            execution.setVariable("invalidSave", false);
        } catch (Exception e) {
            execution.setVariable("invalidSave", true);
        }

        User user = identityService.newUser(systemUserForm.get("username").toString());
        user.setPassword(systemUserForm.get("password").toString());
        user.setFirstName(systemUserForm.get("firstname").toString());
        user.setLastName(systemUserForm.get("lastname").toString());
        user.setEmail(systemUserForm.get("email").toString());

        try {
            identityService.saveUser(user);
            identityService.createMembership(user.getId(), group);
            execution.setVariable("invalidSave", false);
        } catch (Exception e) {
            execution.setVariable("invalidSave", true);
        }

        String token = UUID.randomUUID().toString();
        systemUserService.createVerificationToken(newSysUser, token);

        execution.setVariable("email", newSysUser.getEmail());
        execution.setVariable("verificationToken", token);
    }
}
